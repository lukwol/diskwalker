package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.di.DomainModule
import com.diskusage.domain.stubs.DiskEntryStubs
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.ksp.generated.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class GetDepthTest : KoinTest {

    private val getDepth by inject<GetDepth>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(DomainModule().module)
    }

    @Test
    fun `from disk entry was not passed`() {
        getDepth(DiskEntryStubs.file12) shouldBe 3
    }

    @Nested
    inner class FromSame {
        @Test
        fun `file from file without parent`() {
            getDepth(DiskEntryStubs.rootFile, DiskEntryStubs.rootFile) shouldBe 1
        }

        @Test
        fun `file from file with parent`() {
            getDepth(DiskEntryStubs.file1, DiskEntryStubs.file1) shouldBe 1
        }

        @Test
        fun `dir from dir without parent`() {
            getDepth(DiskEntryStubs.rootDir, DiskEntryStubs.rootDir) shouldBe 1
        }

        @Test
        fun `dir from dir with parent`() {
            getDepth(DiskEntryStubs.dir1, DiskEntryStubs.dir1) shouldBe 1
        }
    }

    @Nested
    inner class FromAncestor {
        @Test
        fun `child file from root dir`() {
            getDepth(DiskEntryStubs.file1, DiskEntryStubs.rootDir) shouldBe 2
        }

        @Test
        fun `grandchild file from root dir`() {
            getDepth(DiskEntryStubs.file11, DiskEntryStubs.rootDir) shouldBe 3
        }

        @Test
        fun `great-grandchild file from root dir`() {
            getDepth(DiskEntryStubs.file112, DiskEntryStubs.rootDir) shouldBe 4
        }

        @Test
        fun `child dir from root dir`() {
            getDepth(DiskEntryStubs.dir2, DiskEntryStubs.rootDir) shouldBe 2
        }

        @Test
        fun `grandchild dir from root dir`() {
            getDepth(DiskEntryStubs.dir11, DiskEntryStubs.rootDir) shouldBe 3
        }

        @Test
        fun `grandchild dir from child dir`() {
            getDepth(DiskEntryStubs.dir11, DiskEntryStubs.dir1) shouldBe 2
        }

        @Test
        fun `grandchild file from child dir`() {
            getDepth(DiskEntryStubs.file11, DiskEntryStubs.dir1) shouldBe 2
        }

        @Test
        fun `great-grandchild file from child dir`() {
            getDepth(DiskEntryStubs.file111, DiskEntryStubs.dir1) shouldBe 3
        }

        @Test
        fun `great-grandchild file from grandchild dir`() {
            getDepth(DiskEntryStubs.file111, DiskEntryStubs.dir11) shouldBe 2
        }
    }

    @Nested
    inner class FromDescendant {
        @Test
        fun `root dir from child file`() {
            getDepth(DiskEntryStubs.rootDir, DiskEntryStubs.file1) shouldBe 0
        }

        @Test
        fun `root dir from grandchild file`() {
            getDepth(DiskEntryStubs.rootDir, DiskEntryStubs.file11) shouldBe 0
        }

        @Test
        fun `root dir from great-grandchild file`() {
            getDepth(DiskEntryStubs.rootDir, DiskEntryStubs.file112) shouldBe 0
        }

        @Test
        fun `root dir from child dir`() {
            getDepth(DiskEntryStubs.rootDir, DiskEntryStubs.dir2) shouldBe 0
        }

        @Test
        fun `root dir from grandchild dir`() {
            getDepth(DiskEntryStubs.rootDir, DiskEntryStubs.dir11) shouldBe 0
        }

        @Test
        fun `child dir from grandchild dir`() {
            getDepth(DiskEntryStubs.dir1, DiskEntryStubs.dir11) shouldBe 0
        }

        @Test
        fun `child dir from grandchild file`() {
            getDepth(DiskEntryStubs.dir1, DiskEntryStubs.file11) shouldBe 0
        }

        @Test
        fun `child dir from great-grandchild file`() {
            getDepth(DiskEntryStubs.dir1, DiskEntryStubs.file111) shouldBe 0
        }

        @Test
        fun `grandchild dir from great-grandchild file`() {
            getDepth(DiskEntryStubs.dir11, DiskEntryStubs.file111) shouldBe 0
        }
    }

    @Nested
    inner class FromSibling {
        @Test
        fun `file from file`() {
            getDepth(DiskEntryStubs.file111, DiskEntryStubs.file112) shouldBe 0
        }

        @Test
        fun `dir from dir`() {
            getDepth(DiskEntryStubs.dir1, DiskEntryStubs.dir2) shouldBe 0
        }

        @Test
        fun `dir from file`() {
            getDepth(DiskEntryStubs.dir1, DiskEntryStubs.file1) shouldBe 0
        }

        @Test
        fun `file from dir`() {
            getDepth(DiskEntryStubs.file12, DiskEntryStubs.dir11) shouldBe 0
        }
    }

    @Nested
    inner class FromUnrelated {
        @Test
        fun `file from file`() {
            getDepth(DiskEntryStubs.file12, DiskEntryStubs.file21) shouldBe 0
        }

        @Test
        fun `dir from dir`() {
            getDepth(DiskEntryStubs.dir11, DiskEntryStubs.dir2) shouldBe 0
        }

        @Test
        fun `dir from file`() {
            getDepth(DiskEntryStubs.dir2, DiskEntryStubs.file111) shouldBe 0
        }

        @Test
        fun `file from dir`() {
            getDepth(DiskEntryStubs.file12, DiskEntryStubs.dir2) shouldBe 0
        }
    }
}
