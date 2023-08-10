package com.diskwalker.data.services

import com.diskwalker.data.ScanResult
import com.diskwalker.domain.model.disk.DiskInfo
import com.diskwalker.domain.model.errors.ScanCancelled
import com.diskwalker.domain.model.path.PathInfo
import com.diskwalker.domain.services.FileSizeService
import com.diskwalker.domain.usecases.disk.GetDiskInfo
import com.diskwalker.domain.usecases.disk.GetSystemInfo
import io.github.anvell.async.Fail
import io.github.anvell.async.Loading
import io.github.anvell.async.Success
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.nio.file.LinkOption
import java.nio.file.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.io.path.pathString

internal class ScanService(
    private val fileSizeService: FileSizeService,
    private val getDiskInfo: GetDiskInfo,
    private val getSystemInfo: GetSystemInfo,
) {
    fun scanDisk(disk: Path) = callbackFlow {
        val pathChildren = mutableMapOf<Path, MutableSet<Path>>()
        val pathInfo = mutableMapOf<Path, PathInfo>()

        val mutex = Mutex()
        val estimatedTotalSize = getDiskInfo(disk).takenSpace
        val diskMountPoints = getSystemInfo().disks.map(DiskInfo::mountPoint)
        var scannedSize = 0L

        suspend fun traverse(dir: Path) {
            dir.runCatching {
                val entries = listDirectoryEntries()
                    .filterNot { it in diskMountPoints }

                val files = entries
                    .filter { it.isRegularFile(LinkOption.NOFOLLOW_LINKS) }
                    .toMutableSet()

                val dirs = entries
                    .filter { it.isDirectory(LinkOption.NOFOLLOW_LINKS) }

                val filesInfo = files.map {
                    PathInfo(
                        name = it.name,
                        parent = dir,
                        sizeOnDisk = fileSizeService.sizeOnDisk(it.pathString),
                        isFile = true,
                    )
                }

                val filesSize = filesInfo.sumOf(PathInfo::sizeOnDisk)

                val filesMap = files.zip(filesInfo).toMap()

                mutex.withLock {
                    scannedSize += filesSize

                    val progress = (scannedSize * 100 / estimatedTotalSize)
                        .toFloat()
                        .div(100)
                        .coerceAtMost(0.99f)

                    send(Loading(progress))

                    pathChildren[dir] = files
                    pathChildren[dir.parent]?.add(dir)
                    pathInfo.putAll(filesMap)
                }

                dirs
                    .map { async { traverse(it) } }
                    .awaitAll()
            }.onFailure { error ->
                if (error is CancellationException) {
                    send(Fail(ScanCancelled(dir)))
                    close()
                }
            }
        }

        fun directorySize(directory: Path): Long {
            val children = pathChildren[directory].orEmpty()
            val size = children.sumOf {
                pathInfo[it]?.sizeOnDisk ?: directorySize(it)
            }
            pathInfo[directory] = PathInfo(
                name = directory.name.takeIf(String::isNotEmpty) ?: getDiskInfo(disk).name,
                parent = directory.parent,
                sizeOnDisk = size,
                isFile = false,
            )
            return size
        }

        traverse(disk)

        directorySize(disk)

        send(
            Success(
                ScanResult(
                    pathChildren = pathChildren,
                    pathInfo = pathInfo,
                ),
            ),
        )

        close()

        awaitClose()
    }
}
