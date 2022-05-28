package com.diskusage.domain.stubs.usecases.chart.arc

import androidx.compose.ui.geometry.Offset
import com.diskusage.domain.di.domainModule
import com.diskusage.domain.entities.Arc
import com.diskusage.domain.usecases.chart.chartitem.arc.IsArcSelected
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class IsArcSelectedTest : KoinTest {

    private val isArcSelected by inject<IsArcSelected>()

    private val closeOffset = Offset(100f, 100f)
    private val farOffset = Offset(500f, 500f)

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
    }

    @Nested
    inner class HiddenDiskEntryArc {

        private lateinit var arc: Arc

        @BeforeEach
        internal fun setUp() {
            arc = Arc(
                startAngle = 0f,
                sweepAngle = 360f,
                depth = 0f
            )
        }

        @Test
        fun `close offset`() {
            isArcSelected(arc = arc, offset = closeOffset) shouldBe false
        }

        @Test
        fun `far offset`() {
            isArcSelected(arc = arc, offset = farOffset) shouldBe false
        }

        @Test
        fun `zero offset`() {
            isArcSelected(arc = arc, offset = Offset.Zero) shouldBe false
        }

        @Test
        fun `infinite offset`() {
            isArcSelected(arc = arc, offset = Offset.Infinite) shouldBe false
        }

        @Test
        fun `unspecified offset`() {
            shouldThrow<java.lang.IllegalStateException> {
                isArcSelected(arc = arc, offset = Offset.Unspecified)
            }
        }
    }

    @Nested
    inner class SelectedDiskEntryArc {

        private lateinit var arc: Arc

        @BeforeEach
        internal fun setUp() {
            arc = Arc(
                startAngle = 0f,
                sweepAngle = 360f,
                depth = 1f
            )
        }

        @Test
        fun `close offset`() {
            isArcSelected(arc = arc, offset = closeOffset) shouldBe false
        }

        @Test
        fun `far offset`() {
            isArcSelected(arc = arc, offset = farOffset) shouldBe false
        }

        @Test
        fun `zero offset`() {
            isArcSelected(arc = arc, offset = Offset.Zero) shouldBe true
        }

        @Test
        fun `infinite offset`() {
            isArcSelected(arc = arc, offset = Offset.Infinite) shouldBe false
        }

        @Test
        fun `unspecified offset`() {
            shouldThrow<java.lang.IllegalStateException> {
                isArcSelected(arc = arc, offset = Offset.Unspecified)
            }
        }
    }

    @Nested
    inner class CloseDiskEntryArc {

        private lateinit var arc: Arc

        @BeforeEach
        internal fun setUp() {
            arc = Arc(
                startAngle = 30f,
                sweepAngle = 90f,
                depth = 2f
            )
        }

        @Test
        fun `close offset`() {
            isArcSelected(arc = arc, offset = closeOffset) shouldBe true
        }

        @Test
        fun `far offset`() {
            isArcSelected(arc = arc, offset = farOffset) shouldBe false
        }

        @Test
        fun `zero offset`() {
            isArcSelected(arc = arc, offset = Offset.Zero) shouldBe false
        }

        @Test
        fun `infinite offset`() {
            isArcSelected(arc = arc, offset = Offset.Infinite) shouldBe false
        }

        @Test
        fun `unspecified offset`() {
            shouldThrow<java.lang.IllegalStateException> {
                isArcSelected(arc = arc, offset = Offset.Unspecified)
            }
        }
    }

    @Nested
    inner class FarDiskEntryArc {

        private lateinit var arc: Arc

        @BeforeEach
        internal fun setUp() {
            arc = Arc(
                startAngle = 30f,
                sweepAngle = 60f,
                depth = 8f
            )
        }

        @Test
        fun `close offset`() {
            isArcSelected(arc = arc, offset = closeOffset) shouldBe false
        }

        @Test
        fun `far offset`() {
            isArcSelected(arc = arc, offset = farOffset) shouldBe true
        }

        @Test
        fun `zero offset`() {
            isArcSelected(arc = arc, offset = Offset.Zero) shouldBe false
        }

        @Test
        fun `infinite offset`() {
            isArcSelected(arc = arc, offset = Offset.Infinite) shouldBe false
        }

        @Test
        fun `unspecified offset`() {
            shouldThrow<java.lang.IllegalStateException> {
                isArcSelected(arc = arc, offset = Offset.Unspecified)
            }
        }
    }

    @Nested
    inner class EdgeCases {
        private lateinit var arc0: Arc
        private lateinit var arc1: Arc
        private lateinit var arc2: Arc

        @BeforeEach
        internal fun setUp() {
            arc0 = Arc(
                startAngle = 0f,
                sweepAngle = 360f,
                depth = 1f
            )
            arc1 = Arc(
                startAngle = 0f,
                sweepAngle = 45f,
                depth = 2f
            )
            arc2 = Arc(
                startAngle = 45f,
                sweepAngle = 60f,
                depth = 2f
            )
        }

        @Test
        fun `between arc1 and arc2`() {
            isArcSelected(arc = arc1, offset = closeOffset) shouldBe false
            isArcSelected(arc = arc2, offset = closeOffset) shouldBe true
        }

        @Test
        fun `between arc0 and arc2`() {
            isArcSelected(arc = arc0, offset = Offset(0f, 100f)) shouldBe false
            isArcSelected(arc = arc2, offset = Offset(0f, 100f)) shouldBe true
        }
    }
}
