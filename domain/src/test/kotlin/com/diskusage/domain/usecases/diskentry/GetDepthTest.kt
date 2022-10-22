package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.stubs.DiskEntries
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class GetDepthTest : KoinTest {

    private val getDepth by inject<GetDepth>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
    }

    @Test
    fun `from disk entry was not passed`() {
        getDepth(DiskEntries.file12) shouldBe 3
    }

    @Nested
    inner class FromSame {
        @Test
        fun `file from file without parent`() {
            getDepth(DiskEntries.rootFile, DiskEntries.rootFile) shouldBe 1
        }

        @Test
        fun `file from file with parent`() {
            getDepth(DiskEntries.file1, DiskEntries.file1) shouldBe 1
        }

        @Test
        fun `dir from dir without parent`() {
            getDepth(DiskEntries.rootDir, DiskEntries.rootDir) shouldBe 1
        }

        @Test
        fun `dir from dir with parent`() {
            getDepth(DiskEntries.dir1, DiskEntries.dir1) shouldBe 1
        }
    }

    @Nested
    inner class FromAncestor {
        @Test
        fun `child file from root dir`() {
            getDepth(DiskEntries.file1, DiskEntries.rootDir) shouldBe 2
        }

        @Test
        fun `grandchild file from root dir`() {
            getDepth(DiskEntries.file11, DiskEntries.rootDir) shouldBe 3
        }

        @Test
        fun `great-grandchild file from root dir`() {
            getDepth(DiskEntries.file112, DiskEntries.rootDir) shouldBe 4
        }

        @Test
        fun `child dir from root dir`() {
            getDepth(DiskEntries.dir2, DiskEntries.rootDir) shouldBe 2
        }

        @Test
        fun `grandchild dir from root dir`() {
            getDepth(DiskEntries.dir11, DiskEntries.rootDir) shouldBe 3
        }

        @Test
        fun `grandchild dir from child dir`() {
            getDepth(DiskEntries.dir11, DiskEntries.dir1) shouldBe 2
        }

        @Test
        fun `grandchild file from child dir`() {
            getDepth(DiskEntries.file11, DiskEntries.dir1) shouldBe 2
        }

        @Test
        fun `great-grandchild file from child dir`() {
            getDepth(DiskEntries.file111, DiskEntries.dir1) shouldBe 3
        }

        @Test
        fun `great-grandchild file from grandchild dir`() {
            getDepth(DiskEntries.file111, DiskEntries.dir11) shouldBe 2
        }
    }

    @Nested
    inner class FromDescendant {
        @Test
        fun `root dir from child file`() {
            getDepth(DiskEntries.rootDir, DiskEntries.file1) shouldBe 0
        }

        @Test
        fun `root dir from grandchild file`() {
            getDepth(DiskEntries.rootDir, DiskEntries.file11) shouldBe 0
        }

        @Test
        fun `root dir from great-grandchild file`() {
            getDepth(DiskEntries.rootDir, DiskEntries.file112) shouldBe 0
        }

        @Test
        fun `root dir from child dir`() {
            getDepth(DiskEntries.rootDir, DiskEntries.dir2) shouldBe 0
        }

        @Test
        fun `root dir from grandchild dir`() {
            getDepth(DiskEntries.rootDir, DiskEntries.dir11) shouldBe 0
        }

        @Test
        fun `child dir from grandchild dir`() {
            getDepth(DiskEntries.dir1, DiskEntries.dir11) shouldBe 0
        }

        @Test
        fun `child dir from grandchild file`() {
            getDepth(DiskEntries.dir1, DiskEntries.file11) shouldBe 0
        }

        @Test
        fun `child dir from great-grandchild file`() {
            getDepth(DiskEntries.dir1, DiskEntries.file111) shouldBe 0
        }

        @Test
        fun `grandchild dir from great-grandchild file`() {
            getDepth(DiskEntries.dir11, DiskEntries.file111) shouldBe 0
        }
    }

    @Nested
    inner class FromSibling {
        @Test
        fun `file from file`() {
            getDepth(DiskEntries.file111, DiskEntries.file112) shouldBe 0
        }

        @Test
        fun `dir from dir`() {
            getDepth(DiskEntries.dir1, DiskEntries.dir2) shouldBe 0
        }

        @Test
        fun `dir from file`() {
            getDepth(DiskEntries.dir1, DiskEntries.file1) shouldBe 0
        }

        @Test
        fun `file from dir`() {
            getDepth(DiskEntries.file12, DiskEntries.dir11) shouldBe 0
        }
    }

    @Nested
    inner class FromUnrelated {
        @Test
        fun `file from file`() {
            getDepth(DiskEntries.file12, DiskEntries.file21) shouldBe 0
        }

        @Test
        fun `dir from dir`() {
            getDepth(DiskEntries.dir11, DiskEntries.dir2) shouldBe 0
        }

        @Test
        fun `dir from file`() {
            getDepth(DiskEntries.dir2, DiskEntries.file111) shouldBe 0
        }

        @Test
        fun `file from dir`() {
            getDepth(DiskEntries.file12, DiskEntries.dir2) shouldBe 0
        }
    }
}
