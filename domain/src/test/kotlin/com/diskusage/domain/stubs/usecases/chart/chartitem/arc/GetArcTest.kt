package com.diskusage.domain.stubs.usecases.chart.chartitem.arc

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.entities.Arc
import com.diskusage.domain.stubs.stubs.DiskEntryStubs
import com.diskusage.domain.usecases.chart.chartitem.arc.GetArc
import com.diskusage.domain.usecases.chart.chartitem.arc.GetStartAngle
import com.diskusage.domain.usecases.chart.chartitem.arc.GetSweepAngle
import com.diskusage.domain.usecases.diskentry.GetDepth
import io.kotest.matchers.shouldBe
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
        getStartAngle = declareMock {
            every { this@declareMock(any(), DiskEntryStubs.dir1) } returns 25f
            every { this@declareMock(any(), DiskEntryStubs.rootDir) } returns 100f
        }
        getSweepAngle = declareMock {
            every { this@declareMock(any(), DiskEntryStubs.dir1) } returns 60f
            every { this@declareMock(any(), DiskEntryStubs.rootDir) } returns 200f
        }
        getDepth = declareMock {
            every { this@declareMock(any(), DiskEntryStubs.dir1) } returns 1
            every { this@declareMock(any(), DiskEntryStubs.rootDir) } returns 2
        }
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `from disk entry was passed`() {
        val arc = getArc(DiskEntryStubs.file12, DiskEntryStubs.dir1)
        arc shouldBe Arc(
            angleRange = 25f..85f,
            radiusRange = 0f..100f
        )

        verify { getStartAngle.invoke(DiskEntryStubs.file12, DiskEntryStubs.dir1) }
        verify { getSweepAngle.invoke(DiskEntryStubs.file12, DiskEntryStubs.dir1) }
        verify { getDepth.invoke(DiskEntryStubs.file12, DiskEntryStubs.dir1) }
    }

    @Test
    fun `from disk entry was not passed`() {
        val arc = getArc(DiskEntryStubs.file12)
        arc shouldBe Arc(
            angleRange = 100f..300f,
            radiusRange = 100f..200f
        )
        verify { getStartAngle.invoke(DiskEntryStubs.file12, DiskEntryStubs.rootDir) }
        verify { getSweepAngle.invoke(DiskEntryStubs.file12, DiskEntryStubs.rootDir) }
        verify { getDepth.invoke(DiskEntryStubs.file12, DiskEntryStubs.rootDir) }
    }
}
