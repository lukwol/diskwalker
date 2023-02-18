package com.diskusage.data.services

import com.diskusage.domain.common.Constants
import com.diskusage.domain.model.DiskInfo
import com.diskusage.domain.model.scan.ScanItem
import com.diskusage.domain.model.scan.ScanResult
import com.diskusage.domain.services.FileSizeService
import com.diskusage.domain.usecases.disk.GetDiskName
import com.diskusage.domain.usecases.disk.GetDiskTakenSpace
import com.diskusage.domain.usecases.disk.GetDiskTotalSpace
import io.github.anvell.async.Loading
import io.github.anvell.async.Success
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

class ScanService(
    private val fileSizeService: FileSizeService,
    private val getDiskName: GetDiskName,
    private val getDiskTakenSpace: GetDiskTakenSpace,
    private val getDiskTotalSpace: GetDiskTotalSpace
) {
    suspend fun scanDisk(disk: Path) = callbackFlow {
        val allChildren = mutableMapOf<Path, MutableSet<Path>>()
        val allScanItems = mutableMapOf<Path, ScanItem>()

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
                    ScanItem.File(fileSizeService.sizeOnDisk(it.pathString))
                }

                val filesSize = filesInfo.sumOf(ScanItem.File::size)

                val filesMap = files.zip(filesInfo).toMap()

                mutex.withLock {
                    scannedSize += filesSize

                    val progress = (scannedSize * 100 / estimatedTotalSize)
                        .toFloat()
                        .div(100)
                        .coerceAtMost(0.99f)

                    send(Loading(progress))

                    allChildren[dir] = files
                    allChildren[dir.parent]?.add(dir)
                    allScanItems.putAll(filesMap)
                }

                dirs.map { dir ->
                    async { traverse(dir) }
                }.awaitAll()
            }
        }

        fun directorySize(directory: Path): Long {
            val children = allChildren[directory].orEmpty()
            val size = children.sumOf {
                allScanItems[it]?.size ?: directorySize(it)
            }
            allScanItems[directory] = ScanItem.Directory(size)
            return size
        }

        traverse(disk)

        directorySize(disk)

        send(
            Success(
                ScanResult(
                    diskInfo = DiskInfo(
                        path = disk,
                        name = getDiskName(disk),
                        takenSpace = getDiskTakenSpace(disk),
                        totalSpace = getDiskTotalSpace(disk)
                    ),
                    children = allChildren,
                    scanItems = allScanItems
                )
            )
        )

        close()

        awaitClose()
    }
}
