package com.diskusage.data.repositories

import com.diskusage.data.services.ScanService
import com.diskusage.domain.model.scan.ScanException
import com.diskusage.domain.model.scan.ScanResult
import com.diskusage.domain.services.ScanRepository
import io.github.anvell.async.Fail
import io.github.anvell.async.Loading
import io.github.anvell.async.Success
import io.github.anvell.async.Uninitialized
import kotlinx.coroutines.flow.map
import java.nio.file.Path

class ScanRepositoryImpl(
    private val scanService: ScanService
) : ScanRepository {

    private var scanResult: ScanResult? = null

    override suspend fun scanDisk(disk: Path) = scanService
        .scanDisk(disk)
        .map {
            when (it) {
                is Fail -> throw it.error
                is Loading -> it.progress ?: 0f
                is Success -> {
                    scanResult = it.value
                    1f
                }
                Uninitialized -> 0f
            }
        }

    override fun diskInfo() = scanResult
        .run { this ?: throw ScanException.ScanNotPerformed }
        .diskInfo

    override fun scanItem(path: Path) = scanResult
        .run { this ?: throw ScanException.ScanNotPerformed }
        .scanItems[path] ?: throw ScanException.MissingScanItem(path)

    override fun children(path: Path) = scanResult
        .run { this ?: throw ScanException.ScanNotPerformed }
        .children[path] ?: throw ScanException.MissingChildren(path)
}
