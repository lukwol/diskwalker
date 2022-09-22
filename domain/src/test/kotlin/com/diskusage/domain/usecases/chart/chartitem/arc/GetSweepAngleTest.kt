package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.stubs.DiskEntryStubs
import com.diskusage.domain.usecases.chart.item.arc.GetSweepAngle
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class GetSweepAngleTest : KoinTest {

    private val getSweepAngle by inject<GetSweepAngle>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
    }

    @Test
    fun `from disk entry was not passed`() {
        getSweepAngle(DiskEntryStubs.file12) shouldBe 58f.plusOrMinus(1f)
    }

    @Nested
    inner class FromSame {
        @Test
        fun `file from file without parent`() {
            getSweepAngle(DiskEntryStubs.rootFile, DiskEntryStubs.rootFile) shouldBe 360f
        }

        @Test
        fun `file from file with parent`() {
            getSweepAngle(DiskEntryStubs.file1, DiskEntryStubs.file1) shouldBe 360f
        }

        @Test
        fun `dir from dir without parent`() {
            getSweepAngle(DiskEntryStubs.rootDir, DiskEntryStubs.rootDir) shouldBe 360f
        }

        @Test
        fun `dir from dir with parent`() {
            getSweepAngle(DiskEntryStubs.dir1, DiskEntryStubs.dir1) shouldBe 360f
        }
    }

    @Nested
    inner class FromAncestor {
        @Test
        fun `child file from root dir`() {
            getSweepAngle(DiskEntryStubs.file1, DiskEntryStubs.rootDir) shouldBe 144f
        }

        @Test
        fun `grandchild file from root dir`() {
            getSweepAngle(DiskEntryStubs.file11, DiskEntryStubs.rootDir) shouldBe 29f.plusOrMinus(1f)
        }

        @Test
        fun `great-grandchild file from root dir`() {
            getSweepAngle(DiskEntryStubs.file112, DiskEntryStubs.rootDir) shouldBe 8f.plusOrMinus(1f)
        }

        @Test
        fun `child dir from root dir`() {
            getSweepAngle(DiskEntryStubs.dir2, DiskEntryStubs.rootDir) shouldBe 108f.plusOrMinus(1f)
        }

        @Test
        fun `grandchild dir from root dir`() {
            getSweepAngle(DiskEntryStubs.dir11, DiskEntryStubs.rootDir) shouldBe 22f.plusOrMinus(1f)
        }

        @Test
        fun `grandchild dir from child dir`() {
            getSweepAngle(DiskEntryStubs.dir11, DiskEntryStubs.dir1) shouldBe 72f
        }

        @Test
        fun `grandchild file from child dir`() {
            getSweepAngle(DiskEntryStubs.file11, DiskEntryStubs.dir1) shouldBe 96f.plusOrMinus(1f)
        }

        @Test
        fun `great-grandchild file from child dir`() {
            getSweepAngle(DiskEntryStubs.file111, DiskEntryStubs.dir1) shouldBe 48f.plusOrMinus(1f)
        }

        @Test
        fun `great-grandchild file from grandchild dir`() {
            getSweepAngle(DiskEntryStubs.file111, DiskEntryStubs.dir11) shouldBe 240f
        }
    }

    @Nested
    inner class FromDescendant {
        @Test
        fun `root dir from child file`() {
            getSweepAngle(DiskEntryStubs.rootDir, DiskEntryStubs.file1) shouldBe 360f
        }

        @Test
        fun `root dir from grandchild file`() {
            getSweepAngle(DiskEntryStubs.rootDir, DiskEntryStubs.file11) shouldBe 360f
        }

        @Test
        fun `root dir from great-grandchild file`() {
            getSweepAngle(DiskEntryStubs.rootDir, DiskEntryStubs.file112) shouldBe 360f
        }

        @Test
        fun `root dir from child dir`() {
            getSweepAngle(DiskEntryStubs.rootDir, DiskEntryStubs.dir2) shouldBe 360f
        }

        @Test
        fun `root dir from grandchild dir`() {
            getSweepAngle(DiskEntryStubs.rootDir, DiskEntryStubs.dir11) shouldBe 360f
        }

        @Test
        fun `child dir from grandchild dir`() {
            getSweepAngle(DiskEntryStubs.dir1, DiskEntryStubs.dir11) shouldBe 360f
        }

        @Test
        fun `child dir from grandchild file`() {
            getSweepAngle(DiskEntryStubs.dir1, DiskEntryStubs.file11) shouldBe 360f
        }

        @Test
        fun `child dir from great-grandchild file`() {
            getSweepAngle(DiskEntryStubs.dir1, DiskEntryStubs.file111) shouldBe 360f
        }

        @Test
        fun `grandchild dir from great-grandchild file`() {
            getSweepAngle(DiskEntryStubs.dir11, DiskEntryStubs.file111) shouldBe 360f
        }
    }

    @Nested
    inner class FromSibling {
        @Test
        fun `file from file`() {
            getSweepAngle(DiskEntryStubs.file111, DiskEntryStubs.file112) shouldBe 0f
        }

        @Test
        fun `dir from dir`() {
            getSweepAngle(DiskEntryStubs.dir1, DiskEntryStubs.dir2) shouldBe 0f
        }

        @Test
        fun `dir from file`() {
            getSweepAngle(DiskEntryStubs.dir1, DiskEntryStubs.file1) shouldBe 0f
        }

        @Test
        fun `file from dir`() {
            getSweepAngle(DiskEntryStubs.file12, DiskEntryStubs.dir11) shouldBe 0f
        }
    }

    @Nested
    inner class FromUnrelated {
        @Test
        fun `file from file`() {
            getSweepAngle(DiskEntryStubs.file12, DiskEntryStubs.file21) shouldBe 0f
        }

        @Test
        fun `dir from dir`() {
            getSweepAngle(DiskEntryStubs.dir11, DiskEntryStubs.dir2) shouldBe 0f
        }

        @Test
        fun `dir from file`() {
            getSweepAngle(DiskEntryStubs.dir2, DiskEntryStubs.file111) shouldBe 0f
        }

        @Test
        fun `file from dir`() {
            getSweepAngle(DiskEntryStubs.file12, DiskEntryStubs.dir2) shouldBe 0f
        }
    }
}
