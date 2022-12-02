package com.diskusage.domain.di

import com.diskusage.domain.usecases.chart.GetChartData
import com.diskusage.domain.usecases.chart.item.GetChartItem
import com.diskusage.domain.usecases.chart.item.GetColor
import com.diskusage.domain.usecases.chart.item.arc.*
import com.diskusage.domain.usecases.diskentry.*
import com.diskusage.domain.usecases.list.GetListData
import com.diskusage.domain.usecases.list.item.GetListItem
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
    singleOf(::GetDiskEntry)
    singleOf(::GetDiskEntriesList)
    singleOf(::IncludeDiskEntry)
    singleOf(::SortDiskEntries)
    singleOf(::GetDepth)
    singleOf(::GetRelationship)
    singleOf(::GetRoot)
}
