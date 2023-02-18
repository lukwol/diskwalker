package com.diskusage.data.repositories

import com.diskusage.data.ScanResult
import com.diskusage.data.services.ScanService
import com.diskusage.domain.model.scan.ScanException
import com.diskusage.domain.services.ScanRepository
import io.github.anvell.async.map
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.nio.file.Path

internal class ScanRepositoryImpl(
    private val scanService: ScanService
) : ScanRepository {

    private var lastScanResult: ScanResult? = null

    override fun scanDisk(disk: Path) = scanService
        .scanDisk(disk)
        .onEach { lastScanResult = it.value }
        .map { it.map { } }

    override fun scanItem(path: Path) = lastScanResult
        .run { this ?: throw ScanException.ScanNotPerformed }
        .scanItems[path] ?: throw ScanException.MissingScanItem(path)

    override fun children(path: Path) = lastScanResult
        .run { this ?: throw ScanException.ScanNotPerformed }
        .children[path] ?: throw ScanException.MissingChildren(path)
}
