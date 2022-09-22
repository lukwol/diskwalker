package com.diskusage.domain.di

import com.diskusage.domain.usecases.chart.GetDiskEntriesList
import com.diskusage.domain.usecases.chart.IncludeDiskEntry
import com.diskusage.domain.usecases.chart.SortDiskEntries
import com.diskusage.domain.usecases.chart.item.chart.GetChartData
import com.diskusage.domain.usecases.chart.item.chart.GetChartItem
import com.diskusage.domain.usecases.chart.item.chart.GetColor
import com.diskusage.domain.usecases.chart.item.chart.arc.*
import com.diskusage.domain.usecases.chart.item.list.GetListData
import com.diskusage.domain.usecases.chart.item.list.GetListItem
import com.diskusage.domain.usecases.diskentry.GetDepth
import com.diskusage.domain.usecases.diskentry.GetRelationship
import com.diskusage.domain.usecases.diskentry.GetRoot
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
    singleOf(::GetChartItem)
    singleOf(::GetListData)
    singleOf(::GetChartData)
    singleOf(::GetColor)
    singleOf(::GetDiskEntriesList)
    singleOf(::IncludeDiskEntry)
    singleOf(::SortDiskEntries)
    singleOf(::GetDepth)
    singleOf(::GetRelationship)
    singleOf(::GetRoot)
}
