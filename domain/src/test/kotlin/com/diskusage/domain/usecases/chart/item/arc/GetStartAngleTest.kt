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

class GetStartAngleTest : KoinTest {

    private val getStartAngle by inject<GetStartAngle>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
    }

    @Test
    fun `from disk entry was not passed`() {
        getStartAngle(DiskEntries.file12) shouldBe 144f
    }

    @Nested
    inner class FromSame {
        @Test
        fun `file from file without parent`() {
            getStartAngle(DiskEntries.rootFile, DiskEntries.rootFile) shouldBe 0f
        }

        @Test
        fun `file from file with parent`() {
            getStartAngle(DiskEntries.file1, DiskEntries.file1) shouldBe 0f
        }

        @Test
        fun `dir from dir without parent`() {
            getStartAngle(DiskEntries.rootDir, DiskEntries.rootDir) shouldBe 0f
        }

        @Test
        fun `dir from dir with parent`() {
            getStartAngle(DiskEntries.dir1, DiskEntries.dir1) shouldBe 0f
        }
    }

    @Nested
    inner class FromAncestor {
        @Test
        fun `child file from root dir`() {
            getStartAngle(DiskEntries.file1, DiskEntries.rootDir) shouldBe 0f
        }

        @Test
        fun `grandchild file from root dir`() {
            getStartAngle(DiskEntries.file11, DiskEntries.rootDir) shouldBe 202f.plusOrMinus(1f)
        }

        @Test
        fun `great-grandchild file from root dir`() {
            getStartAngle(DiskEntries.file112, DiskEntries.rootDir) shouldBe 245f.plusOrMinus(1f)
        }

        @Test
        fun `child dir from root dir`() {
            getStartAngle(DiskEntries.dir2, DiskEntries.rootDir) shouldBe 252f
        }

        @Test
        fun `grandchild dir from root dir`() {
            getStartAngle(DiskEntries.dir11, DiskEntries.rootDir) shouldBe 230f.plusOrMinus(1f)
        }

        @Test
        fun `grandchild dir from child dir`() {
            getStartAngle(DiskEntries.dir11, DiskEntries.dir1) shouldBe 288f
        }

        @Test
        fun `grandchild file from child dir`() {
            getStartAngle(DiskEntries.file11, DiskEntries.dir1) shouldBe 192f
        }

        @Test
        fun `great-grandchild file from child dir`() {
            getStartAngle(DiskEntries.file111, DiskEntries.dir1) shouldBe 288f
        }

        @Test
        fun `great-grandchild file from grandchild dir`() {
            getStartAngle(DiskEntries.file111, DiskEntries.dir11) shouldBe 0f
        }
    }

    @Nested
    inner class FromDescendant {
        @Test
        fun `root dir from child file`() {
            getStartAngle(DiskEntries.rootDir, DiskEntries.file1) shouldBe 0f
        }

        @Test
        fun `root dir from grandchild file`() {
            getStartAngle(DiskEntries.rootDir, DiskEntries.file11) shouldBe 0f
        }

        @Test
        fun `root dir from great-grandchild file`() {
            getStartAngle(DiskEntries.rootDir, DiskEntries.file112) shouldBe 0f
        }

        @Test
        fun `root dir from child dir`() {
            getStartAngle(DiskEntries.rootDir, DiskEntries.dir2) shouldBe 0f
        }

        @Test
        fun `root dir from grandchild dir`() {
            getStartAngle(DiskEntries.rootDir, DiskEntries.dir11) shouldBe 0f
        }

        @Test
        fun `child dir from grandchild dir`() {
            getStartAngle(DiskEntries.dir1, DiskEntries.dir11) shouldBe 0f
        }

        @Test
        fun `child dir from grandchild file`() {
            getStartAngle(DiskEntries.dir1, DiskEntries.file11) shouldBe 0f
        }

        @Test
        fun `child dir from great-grandchild file`() {
            getStartAngle(DiskEntries.dir1, DiskEntries.file111) shouldBe 0f
        }

        @Test
        fun `grandchild dir from great-grandchild file`() {
            getStartAngle(DiskEntries.dir11, DiskEntries.file111) shouldBe 0f
        }
    }

    @Nested
    inner class FromSibling {
        @Test
        fun `file from file`() {
            getStartAngle(DiskEntries.file111, DiskEntries.file112) shouldBe 0f
        }

        @Test
        fun `dir from dir`() {
            getStartAngle(DiskEntries.dir1, DiskEntries.dir2) shouldBe 0f
        }

        @Test
        fun `dir from file`() {
            getStartAngle(DiskEntries.dir1, DiskEntries.file1) shouldBe 360f
        }

        @Test
        fun `file from dir`() {
            getStartAngle(DiskEntries.file12, DiskEntries.dir11) shouldBe 0f
        }
    }

    @Nested
    inner class FromUnrelated {
        @Test
        fun `file from file`() {
            getStartAngle(DiskEntries.file12, DiskEntries.file21) shouldBe 0f
        }

        @Test
        fun `dir from dir`() {
            getStartAngle(DiskEntries.dir11, DiskEntries.dir2) shouldBe 0f
        }

        @Test
        fun `dir from file`() {
            getStartAngle(DiskEntries.dir2, DiskEntries.file111) shouldBe 360f
        }

        @Test
        fun `file from dir`() {
            getStartAngle(DiskEntries.file12, DiskEntries.dir2) shouldBe 0f
        }
    }
}
