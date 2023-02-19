package com.diskusage.domain.di

import com.diskusage.domain.usecases.chart.GetChartData
import com.diskusage.domain.usecases.chart.item.GetChartItem
import com.diskusage.domain.usecases.chart.item.GetColor
import com.diskusage.domain.usecases.chart.item.arc.*
import com.diskusage.domain.usecases.disk.GetDiskInfo
import com.diskusage.domain.usecases.disk.GetDiskName
import com.diskusage.domain.usecases.disk.GetDiskTakenSpace
import com.diskusage.domain.usecases.disk.GetDiskTotalSpace
import com.diskusage.domain.usecases.list.GetListData
import com.diskusage.domain.usecases.list.item.GetListItem
import com.diskusage.domain.usecases.path.*
import com.diskusage.domain.usecases.path.GetPathInfo
import com.diskusage.domain.usecases.path.GetSizeOnDisk
import com.diskusage.domain.usecases.scan.GetChildren
import com.diskusage.domain.usecases.scan.ScanDisk
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::GetArc)
    singleOf(::GetStartAngle)
    singleOf(::GetSweepAngle)
    singleOf(::GetStartRadius)
    singleOf(::GetArcWidth)
    singleOf(::IsArcSelected)
    singleOf(::GetListItem)
    singleOf(::GetListData)
    singleOf(::GetChartItem)
    singleOf(::GetChartData)
    singleOf(::GetColor)
    singleOf(::GetPathsSet)
    singleOf(::IncludePath)
    singleOf(::SortPaths)
    singleOf(::GetDepth)
    singleOf(::GetPathRelationship)
    singleOf(::GetRoot)
    singleOf(::ScanDisk)
    singleOf(::GetChildren)
    singleOf(::GetPathInfo)
    singleOf(::GetSizeOnDisk)
    singleOf(::GetDiskInfo)
    singleOf(::GetDiskName)
    singleOf(::GetDiskTotalSpace)
    singleOf(::GetDiskTakenSpace)
}
