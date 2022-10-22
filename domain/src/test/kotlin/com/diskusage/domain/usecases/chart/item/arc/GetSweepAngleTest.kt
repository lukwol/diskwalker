package com.diskusage.domain.usecases.chart.item.arc

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.stubs.DiskEntries
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
        getSweepAngle(DiskEntries.file12) shouldBe 58f.plusOrMinus(1f)
    }

    @Nested
    inner class FromSame {
        @Test
        fun `file from file without parent`() {
            getSweepAngle(DiskEntries.rootFile, DiskEntries.rootFile) shouldBe 360f
        }

        @Test
        fun `file from file with parent`() {
            getSweepAngle(DiskEntries.file1, DiskEntries.file1) shouldBe 360f
        }

        @Test
        fun `dir from dir without parent`() {
            getSweepAngle(DiskEntries.rootDir, DiskEntries.rootDir) shouldBe 360f
        }

        @Test
        fun `dir from dir with parent`() {
            getSweepAngle(DiskEntries.dir1, DiskEntries.dir1) shouldBe 360f
        }
    }

    @Nested
    inner class FromAncestor {
        @Test
        fun `child file from root dir`() {
            getSweepAngle(DiskEntries.file1, DiskEntries.rootDir) shouldBe 144f
        }

        @Test
        fun `grandchild file from root dir`() {
            getSweepAngle(DiskEntries.file11, DiskEntries.rootDir) shouldBe 29f.plusOrMinus(1f)
        }

        @Test
        fun `great-grandchild file from root dir`() {
            getSweepAngle(DiskEntries.file112, DiskEntries.rootDir) shouldBe 8f.plusOrMinus(1f)
        }

        @Test
        fun `child dir from root dir`() {
            getSweepAngle(DiskEntries.dir2, DiskEntries.rootDir) shouldBe 108f.plusOrMinus(1f)
        }

        @Test
        fun `grandchild dir from root dir`() {
            getSweepAngle(DiskEntries.dir11, DiskEntries.rootDir) shouldBe 22f.plusOrMinus(1f)
        }

        @Test
        fun `grandchild dir from child dir`() {
            getSweepAngle(DiskEntries.dir11, DiskEntries.dir1) shouldBe 72f
        }

        @Test
        fun `grandchild file from child dir`() {
            getSweepAngle(DiskEntries.file11, DiskEntries.dir1) shouldBe 96f.plusOrMinus(1f)
        }

        @Test
        fun `great-grandchild file from child dir`() {
            getSweepAngle(DiskEntries.file111, DiskEntries.dir1) shouldBe 48f.plusOrMinus(1f)
        }

        @Test
        fun `great-grandchild file from grandchild dir`() {
            getSweepAngle(DiskEntries.file111, DiskEntries.dir11) shouldBe 240f
        }
    }

    @Nested
    inner class FromDescendant {
        @Test
        fun `root dir from child file`() {
            getSweepAngle(DiskEntries.rootDir, DiskEntries.file1) shouldBe 360f
        }

        @Test
        fun `root dir from grandchild file`() {
            getSweepAngle(DiskEntries.rootDir, DiskEntries.file11) shouldBe 360f
        }

        @Test
        fun `root dir from great-grandchild file`() {
            getSweepAngle(DiskEntries.rootDir, DiskEntries.file112) shouldBe 360f
        }

        @Test
        fun `root dir from child dir`() {
            getSweepAngle(DiskEntries.rootDir, DiskEntries.dir2) shouldBe 360f
        }

        @Test
        fun `root dir from grandchild dir`() {
            getSweepAngle(DiskEntries.rootDir, DiskEntries.dir11) shouldBe 360f
        }

        @Test
        fun `child dir from grandchild dir`() {
            getSweepAngle(DiskEntries.dir1, DiskEntries.dir11) shouldBe 360f
        }

        @Test
        fun `child dir from grandchild file`() {
            getSweepAngle(DiskEntries.dir1, DiskEntries.file11) shouldBe 360f
        }

        @Test
        fun `child dir from great-grandchild file`() {
            getSweepAngle(DiskEntries.dir1, DiskEntries.file111) shouldBe 360f
        }

        @Test
        fun `grandchild dir from great-grandchild file`() {
            getSweepAngle(DiskEntries.dir11, DiskEntries.file111) shouldBe 360f
        }
    }

    @Nested
    inner class FromSibling {
        @Test
        fun `file from file`() {
            getSweepAngle(DiskEntries.file111, DiskEntries.file112) shouldBe 0f
        }

        @Test
        fun `dir from dir`() {
            getSweepAngle(DiskEntries.dir1, DiskEntries.dir2) shouldBe 0f
        }

        @Test
        fun `dir from file`() {
            getSweepAngle(DiskEntries.dir1, DiskEntries.file1) shouldBe 0f
        }

        @Test
        fun `file from dir`() {
            getSweepAngle(DiskEntries.file12, DiskEntries.dir11) shouldBe 0f
        }
    }

    @Nested
    inner class FromUnrelated {
        @Test
        fun `file from file`() {
            getSweepAngle(DiskEntries.file12, DiskEntries.file21) shouldBe 0f
        }

        @Test
        fun `dir from dir`() {
            getSweepAngle(DiskEntries.dir11, DiskEntries.dir2) shouldBe 0f
        }

        @Test
        fun `dir from file`() {
            getSweepAngle(DiskEntries.dir2, DiskEntries.file111) shouldBe 0f
        }

        @Test
        fun `file from dir`() {
            getSweepAngle(DiskEntries.file12, DiskEntries.dir2) shouldBe 0f
        }
    }
}
