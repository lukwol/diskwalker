package com.diskusage.domain.usecases.chart.item

import com.diskusage.domain.common.Constants
import com.diskusage.domain.data.TestArcs
import com.diskusage.domain.data.TestDiskEntries
import com.diskusage.domain.di.domainModule
import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.usecases.chart.item.arc.GetArc
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkClass
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock

class GetChartItemTest : KoinTest {

    private val getChartItem by inject<GetChartItem>()
    private lateinit var getArc: GetArc

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
    }

    @JvmField
    @RegisterExtension
    val mockProvider = MockProviderExtension.create { clazz ->
        mockkClass(clazz)
    }

    @BeforeEach
    fun setUp() {
        getArc = declareMock {
            every { this@declareMock(TestDiskEntries.file11, TestDiskEntries.rootDir) } answers { TestArcs.child1 }
            every { this@declareMock(TestDiskEntries.file12, TestDiskEntries.dir1) } answers { TestArcs.child2 }
        }
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when from item is not passed`() {
        getChartItem(
            diskEntry = TestDiskEntries.file11
        ) shouldBe ChartItem(
            diskEntry = TestDiskEntries.file11,
            arc = TestArcs.child1,
            color = Constants.Chart.FileColor
        )
    }

    @Test
    fun `when from item is passed`() {
        getChartItem(
            diskEntry = TestDiskEntries.file12,
            fromDiskEntry = TestDiskEntries.dir1
        ) shouldBe ChartItem(
            diskEntry = TestDiskEntries.file12,
            arc = TestArcs.child2,
            color = Constants.Chart.FileColor
        )
    }
}
