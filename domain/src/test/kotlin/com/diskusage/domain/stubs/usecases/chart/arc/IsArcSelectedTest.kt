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
}
