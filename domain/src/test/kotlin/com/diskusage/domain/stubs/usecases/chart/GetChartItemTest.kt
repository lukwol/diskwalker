package com.diskusage.domain.stubs.usecases.chart

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.stubs.stubs.ArcStubs
import com.diskusage.domain.stubs.stubs.DiskEntryStubs
import com.diskusage.domain.usecases.chart.chartitem.arc.GetArc
import com.diskusage.domain.usecases.chart.chartitem.GetChartItem
import com.diskusage.domain.usecases.diskentry.GetRoot
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
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
    private lateinit var getRoot: GetRoot

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
        getArc = declareMock {
            every { this@declareMock(any(), any()) } returns ArcStubs.arc
        }
        getRoot = declareMock {
            every { this@declareMock(any()) } returns DiskEntryStubs.rootDir
        }
    }

    @AfterEach
    internal fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `from disk entry was passed`() {
        getChartItem(DiskEntryStubs.file12, DiskEntryStubs.dir1)
        verify { getArc.invoke(DiskEntryStubs.file12, DiskEntryStubs.dir1) }
    }

    @Test
    fun `from disk entry was not passed`() {
        getChartItem(DiskEntryStubs.file12)
        verify { getArc.invoke(DiskEntryStubs.file12, DiskEntryStubs.rootDir) }
    }
}
