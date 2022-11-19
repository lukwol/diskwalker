package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.data.TestDiskEntries
import com.diskusage.domain.di.domainModule
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
        getDepth(TestDiskEntries.file12) shouldBe 3
    }

    @Nested
    inner class FromSame {
        @Test
        fun `file from file without parent`() {
            getDepth(TestDiskEntries.rootFile, TestDiskEntries.rootFile) shouldBe 1
        }

        @Test
        fun `file from file with parent`() {
            getDepth(TestDiskEntries.file1, TestDiskEntries.file1) shouldBe 1
        }

        @Test
        fun `dir from dir without parent`() {
            getDepth(TestDiskEntries.rootDir, TestDiskEntries.rootDir) shouldBe 1
        }

        @Test
        fun `dir from dir with parent`() {
            getDepth(TestDiskEntries.dir1, TestDiskEntries.dir1) shouldBe 1
        }
    }

    @Nested
    inner class FromAncestor {
        @Test
        fun `child file from root dir`() {
            getDepth(TestDiskEntries.file1, TestDiskEntries.rootDir) shouldBe 2
        }

        @Test
        fun `grandchild file from root dir`() {
            getDepth(TestDiskEntries.file11, TestDiskEntries.rootDir) shouldBe 3
        }

        @Test
        fun `great-grandchild file from root dir`() {
            getDepth(TestDiskEntries.file112, TestDiskEntries.rootDir) shouldBe 4
        }

        @Test
        fun `child dir from root dir`() {
            getDepth(TestDiskEntries.dir2, TestDiskEntries.rootDir) shouldBe 2
        }

        @Test
        fun `grandchild dir from root dir`() {
            getDepth(TestDiskEntries.dir11, TestDiskEntries.rootDir) shouldBe 3
        }

        @Test
        fun `grandchild dir from child dir`() {
            getDepth(TestDiskEntries.dir11, TestDiskEntries.dir1) shouldBe 2
        }

        @Test
        fun `grandchild file from child dir`() {
            getDepth(TestDiskEntries.file11, TestDiskEntries.dir1) shouldBe 2
        }

        @Test
        fun `great-grandchild file from child dir`() {
            getDepth(TestDiskEntries.file111, TestDiskEntries.dir1) shouldBe 3
        }

        @Test
        fun `great-grandchild file from grandchild dir`() {
            getDepth(TestDiskEntries.file111, TestDiskEntries.dir11) shouldBe 2
        }
    }

    @Nested
    inner class FromDescendant {
        @Test
        fun `root dir from child file`() {
            getDepth(TestDiskEntries.rootDir, TestDiskEntries.file1) shouldBe 0
        }

        @Test
        fun `root dir from grandchild file`() {
            getDepth(TestDiskEntries.rootDir, TestDiskEntries.file11) shouldBe 0
        }

        @Test
        fun `root dir from great-grandchild file`() {
            getDepth(TestDiskEntries.rootDir, TestDiskEntries.file112) shouldBe 0
        }

        @Test
        fun `root dir from child dir`() {
            getDepth(TestDiskEntries.rootDir, TestDiskEntries.dir2) shouldBe 0
        }

        @Test
        fun `root dir from grandchild dir`() {
            getDepth(TestDiskEntries.rootDir, TestDiskEntries.dir11) shouldBe 0
        }

        @Test
        fun `child dir from grandchild dir`() {
            getDepth(TestDiskEntries.dir1, TestDiskEntries.dir11) shouldBe 0
        }

        @Test
        fun `child dir from grandchild file`() {
            getDepth(TestDiskEntries.dir1, TestDiskEntries.file11) shouldBe 0
        }

        @Test
        fun `child dir from great-grandchild file`() {
            getDepth(TestDiskEntries.dir1, TestDiskEntries.file111) shouldBe 0
        }

        @Test
        fun `grandchild dir from great-grandchild file`() {
            getDepth(TestDiskEntries.dir11, TestDiskEntries.file111) shouldBe 0
        }
    }

    @Nested
    inner class FromSibling {
        @Test
        fun `file from file`() {
            getDepth(TestDiskEntries.file111, TestDiskEntries.file112) shouldBe 0
        }

        @Test
        fun `dir from dir`() {
            getDepth(TestDiskEntries.dir1, TestDiskEntries.dir2) shouldBe 0
        }

        @Test
        fun `dir from file`() {
            getDepth(TestDiskEntries.dir1, TestDiskEntries.file1) shouldBe 0
        }

        @Test
        fun `file from dir`() {
            getDepth(TestDiskEntries.file12, TestDiskEntries.dir11) shouldBe 0
        }
    }

    @Nested
    inner class FromUnrelated {
        @Test
        fun `file from file`() {
            getDepth(TestDiskEntries.file12, TestDiskEntries.file21) shouldBe 0
        }

        @Test
        fun `dir from dir`() {
            getDepth(TestDiskEntries.dir11, TestDiskEntries.dir2) shouldBe 0
        }

        @Test
        fun `dir from file`() {
            getDepth(TestDiskEntries.dir2, TestDiskEntries.file111) shouldBe 0
        }

        @Test
        fun `file from dir`() {
            getDepth(TestDiskEntries.file12, TestDiskEntries.dir2) shouldBe 0
        }
    }
}
