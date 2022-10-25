package com.diskusage.domain.usecases.chart.item.arc

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.data.TestArcs
import com.diskusage.domain.di.domainModule
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
            isArcSelected(arc = TestArcs.hidden, offset = closeOffset) shouldBe false
        }

        @Test
        fun `far offset`() {
            isArcSelected(arc = TestArcs.hidden, offset = farOffset) shouldBe false
        }

        @Test
        fun `zero offset`() {
            isArcSelected(arc = TestArcs.hidden, offset = Offset.Zero) shouldBe false
        }

        @Test
        fun `infinite offset`() {
            isArcSelected(arc = TestArcs.hidden, offset = Offset.Infinite) shouldBe false
        }

        @Test
        fun `unspecified offset`() {
            shouldThrow<IllegalStateException> {
                isArcSelected(arc = TestArcs.hidden, offset = Offset.Unspecified)
            }
        }
    }

    @Nested
    inner class SelectedDiskEntryArc {

        @Test
        fun `close offset`() {
            isArcSelected(arc = TestArcs.selected, offset = closeOffset) shouldBe false
        }

        @Test
        fun `far offset`() {
            isArcSelected(arc = TestArcs.selected, offset = farOffset) shouldBe false
        }

        @Test
        fun `zero offset`() {
            isArcSelected(arc = TestArcs.selected, offset = Offset.Zero) shouldBe true
        }

        @Test
        fun `infinite offset`() {
            isArcSelected(arc = TestArcs.selected, offset = Offset.Infinite) shouldBe false
        }

        @Test
        fun `unspecified offset`() {
            shouldThrow<IllegalStateException> {
                isArcSelected(arc = TestArcs.selected, offset = Offset.Unspecified)
            }
        }
    }

    @Nested
    inner class CloseDiskEntryArc {

        @Test
        fun `close offset`() {
            isArcSelected(arc = TestArcs.child2, offset = closeOffset) shouldBe true
        }

        @Test
        fun `far offset`() {
            isArcSelected(arc = TestArcs.child2, offset = farOffset) shouldBe false
        }

        @Test
        fun `zero offset`() {
            isArcSelected(arc = TestArcs.child2, offset = Offset.Zero) shouldBe false
        }

        @Test
        fun `infinite offset`() {
            isArcSelected(arc = TestArcs.child2, offset = Offset.Infinite) shouldBe false
        }

        @Test
        fun `unspecified offset`() {
            shouldThrow<IllegalStateException> {
                isArcSelected(arc = TestArcs.child1, offset = Offset.Unspecified)
            }
        }
    }

    @Nested
    inner class FarDiskEntryArc {

        @Test
        fun `close offset`() {
            isArcSelected(arc = TestArcs.far, offset = closeOffset) shouldBe false
        }

        @Test
        fun `far offset`() {
            isArcSelected(arc = TestArcs.far, offset = farOffset) shouldBe true
        }

        @Test
        fun `zero offset`() {
            isArcSelected(arc = TestArcs.far, offset = Offset.Zero) shouldBe false
        }

        @Test
        fun `infinite offset`() {
            isArcSelected(arc = TestArcs.far, offset = Offset.Infinite) shouldBe false
        }

        @Test
        fun `unspecified offset`() {
            shouldThrow<IllegalStateException> {
                isArcSelected(arc = TestArcs.far, offset = Offset.Unspecified)
            }
        }
    }

    @Nested
    inner class EdgeCases {

        @Test
        fun `between arc1 and arc2`() {
            isArcSelected(arc = TestArcs.child1, offset = closeOffset) shouldBe false
            isArcSelected(arc = TestArcs.child2, offset = closeOffset) shouldBe true
        }

        @Test
        fun `between arc0 and arc2`() {
            isArcSelected(arc = TestArcs.selected, offset = Offset(0f, 25f)) shouldBe false
            isArcSelected(arc = TestArcs.child2, offset = Offset(0f, 25f)) shouldBe true
        }
    }
}
