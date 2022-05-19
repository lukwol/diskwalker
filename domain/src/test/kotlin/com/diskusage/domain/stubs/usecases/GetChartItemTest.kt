package com.diskusage.domain.stubs.usecases

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.entities.Arc
import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.entities.DiskEntry
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
import java.nio.file.Path

class GetChartItemTest : KoinTest {

    private val getChartItem by inject<GetChartItem>()

    private val diskEntry = DiskEntry.File(
        name = "foo.txt",
        path = Path.of("/foo.txt"),
        size = 256,
        parent = null,
        hasSizeCalculated = true
    )
    private val arc = Arc(
        startAngle = 120.0f,
        sweepAngle = 45.0f,
        depth = 2.0f
    )
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
            every { this@declareMock(diskEntry) } returns arc
        }
        chartItem = getChartItem(diskEntry)
    }

    @AfterEach
    internal fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should create proper chart item`() {
        chartItem.arc shouldBe arc
        chartItem.color shouldNotBe null
        chartItem.diskEntry shouldBe diskEntry
    }
}
