package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.data.TestDiskEntries
import com.diskusage.domain.di.domainModule
import com.diskusage.domain.model.DiskEntry
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class GetRelationshipTest : KoinTest {

    private val getRelationship by inject<GetRelationship>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
    }

    @Nested
    inner class WithSame {
        @Test
        fun `file from file without parent`() {
            getRelationship(TestDiskEntries.rootFile, TestDiskEntries.rootFile) shouldBe DiskEntry.Relationship.Identity
        }

        @Test
        fun `file from file with parent`() {
            getRelationship(TestDiskEntries.file1, TestDiskEntries.file1) shouldBe DiskEntry.Relationship.Identity
        }

        @Test
        fun `dir from dir without parent`() {
            getRelationship(TestDiskEntries.rootDir, TestDiskEntries.rootDir) shouldBe DiskEntry.Relationship.Identity
        }

        @Test
        fun `dir from dir with parent`() {
            getRelationship(TestDiskEntries.dir1, TestDiskEntries.dir1) shouldBe DiskEntry.Relationship.Identity
        }
    }

    @Nested
    inner class WithAncestor {
        @Test
        fun `child file from root dir`() {
            getRelationship(TestDiskEntries.file1, TestDiskEntries.rootDir) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `grandchild file from root dir`() {
            getRelationship(TestDiskEntries.file11, TestDiskEntries.rootDir) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `great-grandchild file from root dir`() {
            getRelationship(TestDiskEntries.file112, TestDiskEntries.rootDir) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `child dir from root dir`() {
            getRelationship(TestDiskEntries.dir2, TestDiskEntries.rootDir) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `grandchild dir from root dir`() {
            getRelationship(TestDiskEntries.dir11, TestDiskEntries.rootDir) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `grandchild dir from child dir`() {
            getRelationship(TestDiskEntries.dir11, TestDiskEntries.dir1) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `grandchild file from child dir`() {
            getRelationship(TestDiskEntries.file11, TestDiskEntries.dir1) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `great-grandchild file from child dir`() {
            getRelationship(TestDiskEntries.file111, TestDiskEntries.dir1) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `great-grandchild file from grandchild dir`() {
            getRelationship(TestDiskEntries.file111, TestDiskEntries.dir11) shouldBe DiskEntry.Relationship.Ancestor
        }
    }

    @Nested
    inner class WithDescendant {

        @Test
        fun `root dir from child file`() {
            getRelationship(TestDiskEntries.rootDir, TestDiskEntries.file1) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `root dir from grandchild file`() {
            getRelationship(TestDiskEntries.rootDir, TestDiskEntries.file11) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `root dir from great-grandchild file`() {
            getRelationship(TestDiskEntries.rootDir, TestDiskEntries.file112) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `root dir from child dir`() {
            getRelationship(TestDiskEntries.rootDir, TestDiskEntries.dir2) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `root dir from grandchild dir`() {
            getRelationship(TestDiskEntries.rootDir, TestDiskEntries.dir11) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `child dir from grandchild dir`() {
            getRelationship(TestDiskEntries.dir1, TestDiskEntries.dir11) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `child dir from grandchild file`() {
            getRelationship(TestDiskEntries.dir1, TestDiskEntries.file11) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `child dir from great-grandchild file`() {
            getRelationship(TestDiskEntries.dir1, TestDiskEntries.file111) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `grandchild dir from great-grandchild file`() {
            getRelationship(TestDiskEntries.dir11, TestDiskEntries.file111) shouldBe DiskEntry.Relationship.Descendant
        }
    }

    @Nested
    inner class WithSibling {
        @Test
        fun `file from file`() {
            getRelationship(TestDiskEntries.file111, TestDiskEntries.file112) shouldBe DiskEntry.Relationship.Sibling
        }

        @Test
        fun `dir from dir`() {
            getRelationship(TestDiskEntries.dir1, TestDiskEntries.dir2) shouldBe DiskEntry.Relationship.Sibling
        }

        @Test
        fun `dir from file`() {
            getRelationship(TestDiskEntries.dir1, TestDiskEntries.file1) shouldBe DiskEntry.Relationship.Sibling
        }

        @Test
        fun `file from dir`() {
            getRelationship(TestDiskEntries.file12, TestDiskEntries.dir11) shouldBe DiskEntry.Relationship.Sibling
        }
    }

    @Nested
    inner class WithUnrelated {
        @Test
        fun `file from file`() {
            getRelationship(TestDiskEntries.file12, TestDiskEntries.file21) shouldBe DiskEntry.Relationship.Unrelated
        }

        @Test
        fun `dir from dir`() {
            getRelationship(TestDiskEntries.dir11, TestDiskEntries.dir2) shouldBe DiskEntry.Relationship.Unrelated
        }

        @Test
        fun `dir from file`() {
            getRelationship(TestDiskEntries.dir2, TestDiskEntries.file111) shouldBe DiskEntry.Relationship.Unrelated
        }

        @Test
        fun `file from dir`() {
            getRelationship(TestDiskEntries.file12, TestDiskEntries.dir2) shouldBe DiskEntry.Relationship.Unrelated
        }
    }
}
