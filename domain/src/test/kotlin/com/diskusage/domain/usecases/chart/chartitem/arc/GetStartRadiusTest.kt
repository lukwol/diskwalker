package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.common.Constants
import com.diskusage.domain.di.domainModule
import com.diskusage.domain.usecases.chart.item.arc.GetStartRadius
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class GetStartRadiusTest : KoinTest {

    private val getStartRadius by inject<GetStartRadius>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
    }

    @Test
    fun `zero depth`() {
        getStartRadius(0) shouldBe 0f
    }

    @Test
    fun `root item`() {
        getStartRadius(1) shouldBe 0f
    }

    @Test
    fun `child item`() {
        getStartRadius(2) shouldBe Constants.Chart.BigArcWidth
    }

    @Test
    fun `grand child item`() {
        getStartRadius(3) shouldBe 2 * Constants.Chart.BigArcWidth
    }

    @Test
    fun `deeply nested item`() {
        getStartRadius(10) shouldBe 165f
    }
}
