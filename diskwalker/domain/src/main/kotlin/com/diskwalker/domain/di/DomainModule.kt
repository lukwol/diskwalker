package com.diskwalker.domain.di

import com.diskwalker.domain.usecases.chart.GetChartData
import com.diskwalker.domain.usecases.chart.item.GetChartItem
import com.diskwalker.domain.usecases.chart.item.GetColor
import com.diskwalker.domain.usecases.chart.item.arc.GetArc
import com.diskwalker.domain.usecases.chart.item.arc.GetArcWidth
import com.diskwalker.domain.usecases.chart.item.arc.GetStartAngle
import com.diskwalker.domain.usecases.chart.item.arc.GetStartRadius
import com.diskwalker.domain.usecases.chart.item.arc.GetSweepAngle
import com.diskwalker.domain.usecases.chart.item.arc.IsArcSelected
import com.diskwalker.domain.usecases.disk.GetDiskInfo
import com.diskwalker.domain.usecases.disk.GetSystemInfo
import com.diskwalker.domain.usecases.list.GetListData
import com.diskwalker.domain.usecases.list.item.GetListItem
import com.diskwalker.domain.usecases.path.GetDepth
import com.diskwalker.domain.usecases.path.GetName
import com.diskwalker.domain.usecases.path.GetParent
import com.diskwalker.domain.usecases.path.GetPathInfo
import com.diskwalker.domain.usecases.path.GetPathRelationship
import com.diskwalker.domain.usecases.path.GetPathsSet
import com.diskwalker.domain.usecases.path.GetSizeOnDisk
import com.diskwalker.domain.usecases.path.IncludePath
import com.diskwalker.domain.usecases.path.IsFile
import com.diskwalker.domain.usecases.path.SortPaths
import com.diskwalker.domain.usecases.scan.GetChildren
import com.diskwalker.domain.usecases.scan.ScanDisk
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
    singleOf(::ScanDisk)
    singleOf(::GetChildren)
    singleOf(::GetPathInfo)
    singleOf(::GetSizeOnDisk)
    singleOf(::GetName)
    singleOf(::GetParent)
    singleOf(::IsFile)
    singleOf(::GetSystemInfo)
    singleOf(::GetDiskInfo)
}
