package com.diskusage.domain.usecases.chart.chartitem.arc

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.di.domainModule
import com.diskusage.domain.stubs.ArcStubs
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class IsArcSelectedTest : KoinTest {

    private val isArcSelected by inject<IsArcSelected>()

    private val closeOffset = Offset(25f, 25f)
    private val farOffset = Offset(125f, 125f)

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
    }

    @Nested
    inner class HiddenDiskEntryArc {

        @Test
        fun `close offset`() {
            isArcSelected(arc = ArcStubs.hidden, offset = closeOffset) shouldBe false
        }

        @Test
        fun `far offset`() {
            isArcSelected(arc = ArcStubs.hidden, offset = farOffset) shouldBe false
        }

        @Test
        fun `zero offset`() {
            isArcSelected(arc = ArcStubs.hidden, offset = Offset.Zero) shouldBe false
        }

        @Test
        fun `infinite offset`() {
            isArcSelected(arc = ArcStubs.hidden, offset = Offset.Infinite) shouldBe false
        }

        @Test
        fun `unspecified offset`() {
            shouldThrow<java.lang.IllegalStateException> {
                isArcSelected(arc = ArcStubs.hidden, offset = Offset.Unspecified)
            }
        }
    }

    @Nested
    inner class SelectedDiskEntryArc {

        @Test
        fun `close offset`() {
            isArcSelected(arc = ArcStubs.selected, offset = closeOffset) shouldBe false
        }

        @Test
        fun `far offset`() {
            isArcSelected(arc = ArcStubs.selected, offset = farOffset) shouldBe false
        }

        @Test
        fun `zero offset`() {
            isArcSelected(arc = ArcStubs.selected, offset = Offset.Zero) shouldBe true
        }

        @Test
        fun `infinite offset`() {
            isArcSelected(arc = ArcStubs.selected, offset = Offset.Infinite) shouldBe false
        }

        @Test
        fun `unspecified offset`() {
            shouldThrow<java.lang.IllegalStateException> {
                isArcSelected(arc = ArcStubs.selected, offset = Offset.Unspecified)
            }
        }
    }

    @Nested
    inner class CloseDiskEntryArc {

        @Test
        fun `close offset`() {
            isArcSelected(arc = ArcStubs.child2, offset = closeOffset) shouldBe true
        }

        @Test
        fun `far offset`() {
            isArcSelected(arc = ArcStubs.child2, offset = farOffset) shouldBe false
        }

        @Test
        fun `zero offset`() {
            isArcSelected(arc = ArcStubs.child2, offset = Offset.Zero) shouldBe false
        }

        @Test
        fun `infinite offset`() {
            isArcSelected(arc = ArcStubs.child2, offset = Offset.Infinite) shouldBe false
        }

        @Test
        fun `unspecified offset`() {
            shouldThrow<java.lang.IllegalStateException> {
                isArcSelected(arc = ArcStubs.child1, offset = Offset.Unspecified)
            }
        }
    }

    @Nested
    inner class FarDiskEntryArc {

        @Test
        fun `close offset`() {
            isArcSelected(arc = ArcStubs.far, offset = closeOffset) shouldBe false
        }

        @Test
        fun `far offset`() {
            isArcSelected(arc = ArcStubs.far, offset = farOffset) shouldBe true
        }

        @Test
        fun `zero offset`() {
            isArcSelected(arc = ArcStubs.far, offset = Offset.Zero) shouldBe false
        }

        @Test
        fun `infinite offset`() {
            isArcSelected(arc = ArcStubs.far, offset = Offset.Infinite) shouldBe false
        }

        @Test
        fun `unspecified offset`() {
            shouldThrow<java.lang.IllegalStateException> {
                isArcSelected(arc = ArcStubs.far, offset = Offset.Unspecified)
            }
        }
    }

    @Nested
    inner class EdgeCases {

        @Test
        fun `between arc1 and arc2`() {
            isArcSelected(arc = ArcStubs.child1, offset = closeOffset) shouldBe false
            isArcSelected(arc = ArcStubs.child2, offset = closeOffset) shouldBe true
        }

        @Test
        fun `between arc0 and arc2`() {
            isArcSelected(arc = ArcStubs.selected, offset = Offset(0f, 25f)) shouldBe false
            isArcSelected(arc = ArcStubs.child2, offset = Offset(0f, 25f)) shouldBe true
        }
    }
}
