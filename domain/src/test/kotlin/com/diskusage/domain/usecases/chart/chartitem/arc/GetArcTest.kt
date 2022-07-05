package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.common.until
import com.diskusage.domain.di.domainModule
import com.diskusage.domain.entities.Arc
import com.diskusage.domain.stubs.DiskEntryStubs
import com.diskusage.domain.usecases.diskentry.GetDepth
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

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

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `from disk entry was passed`() {
        val arc = getArc(DiskEntryStubs.file12, DiskEntryStubs.dir1)
        arc shouldBe Arc(
            angleRange = 0f until 192f,
            radiusRange = 100f until 200f
        )
    }

    @Test
    fun `from disk entry was not passed`() {
        val arc = getArc(DiskEntryStubs.file12)
        arc shouldBe Arc(
            angleRange = 144f until 201.6f,
            radiusRange = 200f until 300f
        )
    }
}
