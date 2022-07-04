@file:OptIn(ExperimentalGraphicsApi::class)

package com.diskusage.domain.usecases.chart.chartitem

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import com.diskusage.domain.di.domainModule
import com.diskusage.domain.stubs.ArcStubs
import com.diskusage.domain.stubs.DiskEntryStubs
import com.diskusage.domain.usecases.chart.IncludeDiskEntry
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock

class GetColorTest : KoinTest {

    private val getColor by inject<GetColor>()
    private lateinit var includeDiskEntry: IncludeDiskEntry

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

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Nested
    inner class DiskEntryIsVisible {
        @BeforeEach
        fun setUp() {
            includeDiskEntry = declareMock {
                every { this@declareMock(any(), any()) } returns true
            }
        }

        @Test
        fun file() {
            getColor(
                precalculatedArc = ArcStubs.child1,
                diskEntry = DiskEntryStubs.file1
            ) shouldBe Color.hsl(
                hue = 0f,
                saturation = 0f,
                lightness = 0.35f
            )
        }

        @Test
        fun directory() {
            getColor(
                precalculatedArc = ArcStubs.child1,
                diskEntry = DiskEntryStubs.dir11,
            ).run {
                red shouldBe 0.64f.plusOrMinus(0.1f)
                green shouldBe 0.6f.plusOrMinus(0.1f)
                blue shouldBe 0.6f.plusOrMinus(0.1f)
                alpha shouldBe 1f
            }
        }

        @Test
        fun `check if disk entry is included when from disk entry was not passed`() {
            getColor(
                precalculatedArc = ArcStubs.child1,
                diskEntry = DiskEntryStubs.file112
            )
            verify { includeDiskEntry(DiskEntryStubs.file112, DiskEntryStubs.rootDir) }
        }

        @Test
        fun `check if disk entry is included when from disk entry was passed`() {
            getColor(
                precalculatedArc = ArcStubs.child1,
                diskEntry = DiskEntryStubs.dir11,
                fromDiskEntry = DiskEntryStubs.dir1
            )
            verify { includeDiskEntry(DiskEntryStubs.dir11, DiskEntryStubs.dir1) }
        }
    }

    @Nested
    inner class DiskEntryIsHidden {
        @BeforeEach
        fun setUp() {
            includeDiskEntry = declareMock {
                every { this@declareMock(any(), any()) } returns false
            }
        }

        @Test
        fun file() {
            getColor(
                precalculatedArc = ArcStubs.child1,
                diskEntry = DiskEntryStubs.file1
            ) shouldBe Color.hsl(
                hue = 0f,
                saturation = 0f,
                lightness = 0.35f,
                alpha = 0f
            )
        }

        @Test
        fun directory() {
            getColor(
                precalculatedArc = ArcStubs.child2,
                diskEntry = DiskEntryStubs.dir2,
            ).run {
                red shouldBe 0.62f.plusOrMinus(0.01f)
                green shouldBe 0.69f.plusOrMinus(0.01f)
                blue shouldBe 0.6f.plusOrMinus(0.01f)
                alpha shouldBe 0f
            }
        }

        @Test
        fun `check if disk entry is included when from disk entry was not passed`() {
            getColor(
                precalculatedArc = ArcStubs.child1,
                diskEntry = DiskEntryStubs.file112
            )
            verify { includeDiskEntry(DiskEntryStubs.file112, DiskEntryStubs.rootDir) }
        }

        @Test
        fun `check if disk entry is included when from disk entry was passed`() {
            getColor(
                precalculatedArc = ArcStubs.child1,
                diskEntry = DiskEntryStubs.dir11,
                fromDiskEntry = DiskEntryStubs.dir1
            )
            verify { includeDiskEntry(DiskEntryStubs.dir11, DiskEntryStubs.dir1) }
        }
    }
}
