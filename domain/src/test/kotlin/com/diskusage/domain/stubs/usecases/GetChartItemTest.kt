package com.diskusage.domain.stubs.usecases

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.stubs.mocks.ArcStubs
import com.diskusage.domain.stubs.mocks.DiskEntryStubs
import com.diskusage.domain.usecases.GetArc
import com.diskusage.domain.usecases.GetChartItem
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
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

    private lateinit var chartItem: ChartItem

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
    internal fun setUp() {
        declareMock<GetArc> {
            every { this@declareMock(DiskEntryStubs.file1, DiskEntryStubs.rootDir) } returns ArcStubs.arc
        }
        chartItem = getChartItem(DiskEntryStubs.file1, DiskEntryStubs.rootDir)
    }

    @AfterEach
    internal fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `chart item`() {
        chartItem.arc shouldBe ArcStubs.arc
        chartItem.color shouldNotBe null
        chartItem.diskEntry shouldBe DiskEntryStubs.file1
    }
}
