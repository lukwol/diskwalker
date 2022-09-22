package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.common.Constants
import com.diskusage.domain.di.domainModule
import com.diskusage.domain.usecases.chart.item.chart.arc.GetArcWidth
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class GetArcWidthTest : KoinTest {

    private val getArcWidth by inject<GetArcWidth>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
    }

    @Test
    fun `zero depth`() {
        getArcWidth(0) shouldBe 0f
    }

    @Test
    fun `root item`() {
        getArcWidth(1) shouldBe Constants.Chart.BigArcWidth
    }

    @Test
    fun `child item`() {
        getArcWidth(2) shouldBe Constants.Chart.BigArcWidth
    }

    @Test
    fun `grand child item`() {
        getArcWidth(3) shouldBe Constants.Chart.BigArcWidth
    }

    @Test
    fun `deeply nested item`() {
        getArcWidth(10) shouldBe Constants.Chart.SmallArcWidth
    }
}
