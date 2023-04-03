package com.diskwalker.data.repositories

import com.diskwalker.data.ScanResult
import com.diskwalker.data.services.ScanService
import com.diskwalker.domain.model.errors.MissingChildren
import com.diskwalker.domain.model.errors.MissingPathInfo
import com.diskwalker.domain.model.errors.ScanNotPerformed
import com.diskwalker.domain.repositories.ScanRepository
import io.github.anvell.async.map
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import java.nio.file.Path

internal class ScanRepositoryImpl(
    private val scanService: ScanService,
) : ScanRepository {

    private var lastScanResult: ScanResult? = null

    override fun scanDisk(disk: Path) = scanService
        .scanDisk(disk)
        .onEach { lastScanResult = it.value }
        .map { it.map { } }

    override fun pathInfo(path: Path) = lastScanResult
        .run { this ?: throw ScanNotPerformed }
        .pathInfo[path] ?: throw MissingPathInfo(path)

    override fun children(path: Path) = lastScanResult
        .run { this ?: throw ScanNotPerformed }
        .pathChildren[path] ?: throw MissingChildren(path)
}
