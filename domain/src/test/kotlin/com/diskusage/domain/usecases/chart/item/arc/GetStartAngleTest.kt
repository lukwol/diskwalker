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

class GetStartAngleTest : KoinTest {

    private val getStartAngle by inject<GetStartAngle>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
    }

    @Test
    fun `from disk entry was not passed`() {
        getStartAngle(TestDiskEntries.file12) shouldBe 144f
    }

    @Nested
    inner class FromSame {
        @Test
        fun `file from file without parent`() {
            getStartAngle(TestDiskEntries.rootFile, TestDiskEntries.rootFile) shouldBe 0f
        }

        @Test
        fun `file from file with parent`() {
            getStartAngle(TestDiskEntries.file1, TestDiskEntries.file1) shouldBe 0f
        }

        @Test
        fun `dir from dir without parent`() {
            getStartAngle(TestDiskEntries.rootDir, TestDiskEntries.rootDir) shouldBe 0f
        }

        @Test
        fun `dir from dir with parent`() {
            getStartAngle(TestDiskEntries.dir1, TestDiskEntries.dir1) shouldBe 0f
        }
    }

    @Nested
    inner class FromAncestor {
        @Test
        fun `child file from root dir`() {
            getStartAngle(TestDiskEntries.file1, TestDiskEntries.rootDir) shouldBe 0f
        }

        @Test
        fun `grandchild file from root dir`() {
            getStartAngle(TestDiskEntries.file11, TestDiskEntries.rootDir) shouldBe 202f.plusOrMinus(1f)
        }

        @Test
        fun `great-grandchild file from root dir`() {
            getStartAngle(TestDiskEntries.file112, TestDiskEntries.rootDir) shouldBe 245f.plusOrMinus(1f)
        }

        @Test
        fun `child dir from root dir`() {
            getStartAngle(TestDiskEntries.dir2, TestDiskEntries.rootDir) shouldBe 252f
        }

        @Test
        fun `grandchild dir from root dir`() {
            getStartAngle(TestDiskEntries.dir11, TestDiskEntries.rootDir) shouldBe 230f.plusOrMinus(1f)
        }

        @Test
        fun `grandchild dir from child dir`() {
            getStartAngle(TestDiskEntries.dir11, TestDiskEntries.dir1) shouldBe 288f
        }

        @Test
        fun `grandchild file from child dir`() {
            getStartAngle(TestDiskEntries.file11, TestDiskEntries.dir1) shouldBe 192f
        }

        @Test
        fun `great-grandchild file from child dir`() {
            getStartAngle(TestDiskEntries.file111, TestDiskEntries.dir1) shouldBe 288f
        }

        @Test
        fun `great-grandchild file from grandchild dir`() {
            getStartAngle(TestDiskEntries.file111, TestDiskEntries.dir11) shouldBe 0f
        }
    }

    @Nested
    inner class FromDescendant {
        @Test
        fun `root dir from child file`() {
            getStartAngle(TestDiskEntries.rootDir, TestDiskEntries.file1) shouldBe 0f
        }

        @Test
        fun `root dir from grandchild file`() {
            getStartAngle(TestDiskEntries.rootDir, TestDiskEntries.file11) shouldBe 0f
        }

        @Test
        fun `root dir from great-grandchild file`() {
            getStartAngle(TestDiskEntries.rootDir, TestDiskEntries.file112) shouldBe 0f
        }

        @Test
        fun `root dir from child dir`() {
            getStartAngle(TestDiskEntries.rootDir, TestDiskEntries.dir2) shouldBe 0f
        }

        @Test
        fun `root dir from grandchild dir`() {
            getStartAngle(TestDiskEntries.rootDir, TestDiskEntries.dir11) shouldBe 0f
        }

        @Test
        fun `child dir from grandchild dir`() {
            getStartAngle(TestDiskEntries.dir1, TestDiskEntries.dir11) shouldBe 0f
        }

        @Test
        fun `child dir from grandchild file`() {
            getStartAngle(TestDiskEntries.dir1, TestDiskEntries.file11) shouldBe 0f
        }

        @Test
        fun `child dir from great-grandchild file`() {
            getStartAngle(TestDiskEntries.dir1, TestDiskEntries.file111) shouldBe 0f
        }

        @Test
        fun `grandchild dir from great-grandchild file`() {
            getStartAngle(TestDiskEntries.dir11, TestDiskEntries.file111) shouldBe 0f
        }
    }

    @Nested
    inner class FromSibling {
        @Test
        fun `file from file`() {
            getStartAngle(TestDiskEntries.file111, TestDiskEntries.file112) shouldBe 0f
        }

        @Test
        fun `dir from dir`() {
            getStartAngle(TestDiskEntries.dir1, TestDiskEntries.dir2) shouldBe 0f
        }

        @Test
        fun `dir from file`() {
            getStartAngle(TestDiskEntries.dir1, TestDiskEntries.file1) shouldBe 360f
        }

        @Test
        fun `file from dir`() {
            getStartAngle(TestDiskEntries.file12, TestDiskEntries.dir11) shouldBe 0f
        }
    }

    @Nested
    inner class FromUnrelated {
        @Test
        fun `file from file`() {
            getStartAngle(TestDiskEntries.file12, TestDiskEntries.file21) shouldBe 0f
        }

        @Test
        fun `dir from dir`() {
            getStartAngle(TestDiskEntries.dir11, TestDiskEntries.dir2) shouldBe 0f
        }

        @Test
        fun `dir from file`() {
            getStartAngle(TestDiskEntries.dir2, TestDiskEntries.file111) shouldBe 360f
        }

        @Test
        fun `file from dir`() {
            getStartAngle(TestDiskEntries.file12, TestDiskEntries.dir2) shouldBe 0f
        }
    }
}
