package com.diskusage.domain.stubs.usecases.chart.arc

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.stubs.stubs.DiskEntryStubs
import com.diskusage.domain.usecases.chart.chartitem.arc.GetArc
import com.diskusage.domain.usecases.chart.chartitem.arc.GetStartAngle
import com.diskusage.domain.usecases.chart.chartitem.arc.GetSweepAngle
import com.diskusage.domain.usecases.diskentry.GetDepth
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

class GetArcTest : KoinTest {

    private val getArc by inject<GetArc>()

    private lateinit var getStartAngle: GetStartAngle
    private lateinit var getSweepAngle: GetSweepAngle
    private lateinit var getDepth: GetDepth
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
        getStartAngle = declareMock {
            every { this@declareMock(any(), any()) } returns 100f
        }
        getSweepAngle = declareMock {
            every { this@declareMock(any(), any()) } returns 200f
        }
        getDepth = declareMock {
            every { this@declareMock(any(), any()) } returns 2f
        }
    }

    @AfterEach
    internal fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `from disk entry was passed`() {
        getArc(DiskEntryStubs.file12, DiskEntryStubs.dir1)
        verify { getStartAngle.invoke(DiskEntryStubs.file12, DiskEntryStubs.dir1) }
        verify { getSweepAngle.invoke(DiskEntryStubs.file12, DiskEntryStubs.dir1) }
        verify { getDepth.invoke(DiskEntryStubs.file12, DiskEntryStubs.dir1) }
    }

    @Test
    fun `from disk entry was not passed`() {
        getArc(DiskEntryStubs.file12)
        verify { getStartAngle.invoke(DiskEntryStubs.file12, DiskEntryStubs.rootDir) }
        verify { getSweepAngle.invoke(DiskEntryStubs.file12, DiskEntryStubs.rootDir) }
        verify { getDepth.invoke(DiskEntryStubs.file12, DiskEntryStubs.rootDir) }
    }
}
