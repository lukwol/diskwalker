package com.diskusage.domain.usecases.chart.item.arc

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.model.Arc
import com.diskusage.domain.stubs.DiskEntries
import com.diskusage.libraries.ranges.until
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class GetArcTest : KoinTest {

    private val getArc by inject<GetArc>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
    }

    @Test
    fun `from disk entry was passed`() {
        val arc = getArc(DiskEntries.file12, DiskEntries.dir1)
        arc shouldBe Arc(
            angleRange = 0f until 192f,
            radiusRange = 25f until 50f
        )
    }

    @Test
    fun `from disk entry was not passed`() {
        val arc = getArc(DiskEntries.file12)
        arc shouldBe Arc(
            angleRange = 144f until 201.6f,
            radiusRange = 50f until 75f
        )
    }
}
