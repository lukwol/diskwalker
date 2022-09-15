package com.diskusage.domain.di

import com.diskusage.domain.usecases.chart.GetDiskEntriesList
import com.diskusage.domain.usecases.chart.IncludeDiskEntry
import com.diskusage.domain.usecases.chart.SortDiskEntries
import com.diskusage.domain.usecases.chart.chartitem.GetChartItem
import com.diskusage.domain.usecases.chart.chartitem.GetColor
import com.diskusage.domain.usecases.chart.chartitem.GetSortedChartItems
import com.diskusage.domain.usecases.chart.chartitem.arc.*
import com.diskusage.domain.usecases.chart.listItem.GetListItem
import com.diskusage.domain.usecases.chart.listItem.GetSortedListItems
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
    singleOf(::GetSortedListItems)
    singleOf(::GetSortedChartItems)
    singleOf(::GetColor)
    singleOf(::GetDiskEntriesList)
    singleOf(::IncludeDiskEntry)
    singleOf(::SortDiskEntries)
    singleOf(::GetDepth)
    singleOf(::GetRelationship)
    singleOf(::GetRoot)
}
