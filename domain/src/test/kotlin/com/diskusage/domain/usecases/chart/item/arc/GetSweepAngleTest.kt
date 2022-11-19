package com.diskusage.domain.usecases.chart.item.arc

import com.diskusage.domain.data.TestDiskEntries
import com.diskusage.domain.di.domainModule
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
        getSweepAngle(TestDiskEntries.file12) shouldBe 58f.plusOrMinus(1f)
    }

    @Nested
    inner class FromSame {
        @Test
        fun `file from file without parent`() {
            getSweepAngle(TestDiskEntries.rootFile, TestDiskEntries.rootFile) shouldBe 360f
        }

        @Test
        fun `file from file with parent`() {
            getSweepAngle(TestDiskEntries.file1, TestDiskEntries.file1) shouldBe 360f
        }

        @Test
        fun `dir from dir without parent`() {
            getSweepAngle(TestDiskEntries.rootDir, TestDiskEntries.rootDir) shouldBe 360f
        }

        @Test
        fun `dir from dir with parent`() {
            getSweepAngle(TestDiskEntries.dir1, TestDiskEntries.dir1) shouldBe 360f
        }
    }

    @Nested
    inner class FromAncestor {
        @Test
        fun `child file from root dir`() {
            getSweepAngle(TestDiskEntries.file1, TestDiskEntries.rootDir) shouldBe 144f
        }

        @Test
        fun `grandchild file from root dir`() {
            getSweepAngle(TestDiskEntries.file11, TestDiskEntries.rootDir) shouldBe 29f.plusOrMinus(1f)
        }

        @Test
        fun `great-grandchild file from root dir`() {
            getSweepAngle(TestDiskEntries.file112, TestDiskEntries.rootDir) shouldBe 8f.plusOrMinus(1f)
        }

        @Test
        fun `child dir from root dir`() {
            getSweepAngle(TestDiskEntries.dir2, TestDiskEntries.rootDir) shouldBe 108f.plusOrMinus(1f)
        }

        @Test
        fun `grandchild dir from root dir`() {
            getSweepAngle(TestDiskEntries.dir11, TestDiskEntries.rootDir) shouldBe 22f.plusOrMinus(1f)
        }

        @Test
        fun `grandchild dir from child dir`() {
            getSweepAngle(TestDiskEntries.dir11, TestDiskEntries.dir1) shouldBe 72f
        }

        @Test
        fun `grandchild file from child dir`() {
            getSweepAngle(TestDiskEntries.file11, TestDiskEntries.dir1) shouldBe 96f.plusOrMinus(1f)
        }

        @Test
        fun `great-grandchild file from child dir`() {
            getSweepAngle(TestDiskEntries.file111, TestDiskEntries.dir1) shouldBe 48f.plusOrMinus(1f)
        }

        @Test
        fun `great-grandchild file from grandchild dir`() {
            getSweepAngle(TestDiskEntries.file111, TestDiskEntries.dir11) shouldBe 240f
        }
    }

    @Nested
    inner class FromDescendant {
        @Test
        fun `root dir from child file`() {
            getSweepAngle(TestDiskEntries.rootDir, TestDiskEntries.file1) shouldBe 360f
        }

        @Test
        fun `root dir from grandchild file`() {
            getSweepAngle(TestDiskEntries.rootDir, TestDiskEntries.file11) shouldBe 360f
        }

        @Test
        fun `root dir from great-grandchild file`() {
            getSweepAngle(TestDiskEntries.rootDir, TestDiskEntries.file112) shouldBe 360f
        }

        @Test
        fun `root dir from child dir`() {
            getSweepAngle(TestDiskEntries.rootDir, TestDiskEntries.dir2) shouldBe 360f
        }

        @Test
        fun `root dir from grandchild dir`() {
            getSweepAngle(TestDiskEntries.rootDir, TestDiskEntries.dir11) shouldBe 360f
        }

        @Test
        fun `child dir from grandchild dir`() {
            getSweepAngle(TestDiskEntries.dir1, TestDiskEntries.dir11) shouldBe 360f
        }

        @Test
        fun `child dir from grandchild file`() {
            getSweepAngle(TestDiskEntries.dir1, TestDiskEntries.file11) shouldBe 360f
        }

        @Test
        fun `child dir from great-grandchild file`() {
            getSweepAngle(TestDiskEntries.dir1, TestDiskEntries.file111) shouldBe 360f
        }

        @Test
        fun `grandchild dir from great-grandchild file`() {
            getSweepAngle(TestDiskEntries.dir11, TestDiskEntries.file111) shouldBe 360f
        }
    }

    @Nested
    inner class FromSibling {
        @Test
        fun `file from file`() {
            getSweepAngle(TestDiskEntries.file111, TestDiskEntries.file112) shouldBe 0f
        }

        @Test
        fun `dir from dir`() {
            getSweepAngle(TestDiskEntries.dir1, TestDiskEntries.dir2) shouldBe 0f
        }

        @Test
        fun `dir from file`() {
            getSweepAngle(TestDiskEntries.dir1, TestDiskEntries.file1) shouldBe 0f
        }

        @Test
        fun `file from dir`() {
            getSweepAngle(TestDiskEntries.file12, TestDiskEntries.dir11) shouldBe 0f
        }
    }

    @Nested
    inner class FromUnrelated {
        @Test
        fun `file from file`() {
            getSweepAngle(TestDiskEntries.file12, TestDiskEntries.file21) shouldBe 0f
        }

        @Test
        fun `dir from dir`() {
            getSweepAngle(TestDiskEntries.dir11, TestDiskEntries.dir2) shouldBe 0f
        }

        @Test
        fun `dir from file`() {
            getSweepAngle(TestDiskEntries.dir2, TestDiskEntries.file111) shouldBe 0f
        }

        @Test
        fun `file from dir`() {
            getSweepAngle(TestDiskEntries.file12, TestDiskEntries.dir2) shouldBe 0f
        }
    }
}
