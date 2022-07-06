package com.diskusage.domain.usecases.chart.chartitem.arc

import com.diskusage.domain.di.DomainModule
import com.diskusage.domain.stubs.DiskEntryStubs
import io.kotest.matchers.floats.plusOrMinus
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class GetStartAngleTest : KoinTest {

    private val getStartAngle by inject<GetStartAngle>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(DomainModule().module)
    }

    @Test
    fun `from disk entry was not passed`() {
        getStartAngle(DiskEntryStubs.file12) shouldBe 144f
    }

    @Nested
    inner class FromSame {
        @Test
        fun `file from file without parent`() {
            getStartAngle(DiskEntryStubs.rootFile, DiskEntryStubs.rootFile) shouldBe 0f
        }

        @Test
        fun `file from file with parent`() {
            getStartAngle(DiskEntryStubs.file1, DiskEntryStubs.file1) shouldBe 0f
        }

        @Test
        fun `dir from dir without parent`() {
            getStartAngle(DiskEntryStubs.rootDir, DiskEntryStubs.rootDir) shouldBe 0f
        }

        @Test
        fun `dir from dir with parent`() {
            getStartAngle(DiskEntryStubs.dir1, DiskEntryStubs.dir1) shouldBe 0f
        }
    }

    @Nested
    inner class FromAncestor {
        @Test
        fun `child file from root dir`() {
            getStartAngle(DiskEntryStubs.file1, DiskEntryStubs.rootDir) shouldBe 0f
        }

        @Test
        fun `grandchild file from root dir`() {
            getStartAngle(DiskEntryStubs.file11, DiskEntryStubs.rootDir) shouldBe 202f.plusOrMinus(1f)
        }

        @Test
        fun `great-grandchild file from root dir`() {
            getStartAngle(DiskEntryStubs.file112, DiskEntryStubs.rootDir) shouldBe 245f.plusOrMinus(1f)
        }

        @Test
        fun `child dir from root dir`() {
            getStartAngle(DiskEntryStubs.dir2, DiskEntryStubs.rootDir) shouldBe 252f
        }

        @Test
        fun `grandchild dir from root dir`() {
            getStartAngle(DiskEntryStubs.dir11, DiskEntryStubs.rootDir) shouldBe 230f.plusOrMinus(1f)
        }

        @Test
        fun `grandchild dir from child dir`() {
            getStartAngle(DiskEntryStubs.dir11, DiskEntryStubs.dir1) shouldBe 288f
        }

        @Test
        fun `grandchild file from child dir`() {
            getStartAngle(DiskEntryStubs.file11, DiskEntryStubs.dir1) shouldBe 192f
        }

        @Test
        fun `great-grandchild file from child dir`() {
            getStartAngle(DiskEntryStubs.file111, DiskEntryStubs.dir1) shouldBe 288f
        }

        @Test
        fun `great-grandchild file from grandchild dir`() {
            getStartAngle(DiskEntryStubs.file111, DiskEntryStubs.dir11) shouldBe 0f
        }
    }

    @Nested
    inner class FromDescendant {
        @Test
        fun `root dir from child file`() {
            getStartAngle(DiskEntryStubs.rootDir, DiskEntryStubs.file1) shouldBe 0f
        }

        @Test
        fun `root dir from grandchild file`() {
            getStartAngle(DiskEntryStubs.rootDir, DiskEntryStubs.file11) shouldBe 0f
        }

        @Test
        fun `root dir from great-grandchild file`() {
            getStartAngle(DiskEntryStubs.rootDir, DiskEntryStubs.file112) shouldBe 0f
        }

        @Test
        fun `root dir from child dir`() {
            getStartAngle(DiskEntryStubs.rootDir, DiskEntryStubs.dir2) shouldBe 0f
        }

        @Test
        fun `root dir from grandchild dir`() {
            getStartAngle(DiskEntryStubs.rootDir, DiskEntryStubs.dir11) shouldBe 0f
        }

        @Test
        fun `child dir from grandchild dir`() {
            getStartAngle(DiskEntryStubs.dir1, DiskEntryStubs.dir11) shouldBe 0f
        }

        @Test
        fun `child dir from grandchild file`() {
            getStartAngle(DiskEntryStubs.dir1, DiskEntryStubs.file11) shouldBe 0f
        }

        @Test
        fun `child dir from great-grandchild file`() {
            getStartAngle(DiskEntryStubs.dir1, DiskEntryStubs.file111) shouldBe 0f
        }

        @Test
        fun `grandchild dir from great-grandchild file`() {
            getStartAngle(DiskEntryStubs.dir11, DiskEntryStubs.file111) shouldBe 0f
        }
    }

    @Nested
    inner class FromSibling {
        @Test
        fun `file from file`() {
            getStartAngle(DiskEntryStubs.file111, DiskEntryStubs.file112) shouldBe 0f
        }

        @Test
        fun `dir from dir`() {
            getStartAngle(DiskEntryStubs.dir1, DiskEntryStubs.dir2) shouldBe 0f
        }

        @Test
        fun `dir from file`() {
            getStartAngle(DiskEntryStubs.dir1, DiskEntryStubs.file1) shouldBe 360f
        }

        @Test
        fun `file from dir`() {
            getStartAngle(DiskEntryStubs.file12, DiskEntryStubs.dir11) shouldBe 0f
        }
    }

    @Nested
    inner class FromUnrelated {
        @Test
        fun `file from file`() {
            getStartAngle(DiskEntryStubs.file12, DiskEntryStubs.file21) shouldBe 0f
        }

        @Test
        fun `dir from dir`() {
            getStartAngle(DiskEntryStubs.dir11, DiskEntryStubs.dir2) shouldBe 0f
        }

        @Test
        fun `dir from file`() {
            getStartAngle(DiskEntryStubs.dir2, DiskEntryStubs.file111) shouldBe 360f
        }

        @Test
        fun `file from dir`() {
            getStartAngle(DiskEntryStubs.file12, DiskEntryStubs.dir2) shouldBe 0f
        }
    }
}
