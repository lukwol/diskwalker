package com.diskusage.data.services

import com.diskusage.data.ScanResult
import com.diskusage.domain.common.Constants
import com.diskusage.domain.model.errors.ScanCancelled
import com.diskusage.domain.model.path.PathInfo
import com.diskusage.domain.services.FileSizeService
import com.diskusage.domain.usecases.disk.GetDiskTakenSpace
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
import kotlin.io.path.pathString

internal class ScanService(
    private val fileSizeService: FileSizeService,
    private val getDiskTakenSpace: GetDiskTakenSpace,
) {
    fun scanDisk(disk: Path) = callbackFlow {
        val pathChildren = mutableMapOf<Path, MutableSet<Path>>()
        val pathInfo = mutableMapOf<Path, PathInfo>()

        val mutex = Mutex()
        val estimatedTotalSize = getDiskTakenSpace(disk)
        var scannedSize = 0L

        suspend fun traverse(dir: Path) {
            dir.runCatching {
                val entries = listDirectoryEntries()
                    .filterNot { it in Constants.Disk.UncheckedPaths }

                val files = entries
                    .filter { it.isRegularFile(LinkOption.NOFOLLOW_LINKS) }
                    .toMutableSet()

                val dirs = entries
                    .filter { it.isDirectory(LinkOption.NOFOLLOW_LINKS) }

                val filesInfo = files.map {
                    PathInfo.File(fileSizeService.sizeOnDisk(it.pathString))
                }

                val filesSize = filesInfo.sumOf(PathInfo.File::sizeOnDisk)

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
            pathInfo[directory] = PathInfo.Directory(size)
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
