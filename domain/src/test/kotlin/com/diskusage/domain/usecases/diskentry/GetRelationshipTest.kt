package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.model.DiskEntry
import com.diskusage.domain.stubs.DiskEntries
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
            getRelationship(DiskEntries.rootFile, DiskEntries.rootFile) shouldBe DiskEntry.Relationship.Identity
        }

        @Test
        fun `file from file with parent`() {
            getRelationship(DiskEntries.file1, DiskEntries.file1) shouldBe DiskEntry.Relationship.Identity
        }

        @Test
        fun `dir from dir without parent`() {
            getRelationship(DiskEntries.rootDir, DiskEntries.rootDir) shouldBe DiskEntry.Relationship.Identity
        }

        @Test
        fun `dir from dir with parent`() {
            getRelationship(DiskEntries.dir1, DiskEntries.dir1) shouldBe DiskEntry.Relationship.Identity
        }
    }

    @Nested
    inner class WithAncestor {
        @Test
        fun `child file from root dir`() {
            getRelationship(DiskEntries.file1, DiskEntries.rootDir) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `grandchild file from root dir`() {
            getRelationship(DiskEntries.file11, DiskEntries.rootDir) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `great-grandchild file from root dir`() {
            getRelationship(DiskEntries.file112, DiskEntries.rootDir) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `child dir from root dir`() {
            getRelationship(DiskEntries.dir2, DiskEntries.rootDir) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `grandchild dir from root dir`() {
            getRelationship(DiskEntries.dir11, DiskEntries.rootDir) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `grandchild dir from child dir`() {
            getRelationship(DiskEntries.dir11, DiskEntries.dir1) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `grandchild file from child dir`() {
            getRelationship(DiskEntries.file11, DiskEntries.dir1) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `great-grandchild file from child dir`() {
            getRelationship(DiskEntries.file111, DiskEntries.dir1) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `great-grandchild file from grandchild dir`() {
            getRelationship(DiskEntries.file111, DiskEntries.dir11) shouldBe DiskEntry.Relationship.Ancestor
        }
    }

    @Nested
    inner class WithDescendant {

        @Test
        fun `root dir from child file`() {
            getRelationship(DiskEntries.rootDir, DiskEntries.file1) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `root dir from grandchild file`() {
            getRelationship(DiskEntries.rootDir, DiskEntries.file11) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `root dir from great-grandchild file`() {
            getRelationship(DiskEntries.rootDir, DiskEntries.file112) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `root dir from child dir`() {
            getRelationship(DiskEntries.rootDir, DiskEntries.dir2) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `root dir from grandchild dir`() {
            getRelationship(DiskEntries.rootDir, DiskEntries.dir11) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `child dir from grandchild dir`() {
            getRelationship(DiskEntries.dir1, DiskEntries.dir11) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `child dir from grandchild file`() {
            getRelationship(DiskEntries.dir1, DiskEntries.file11) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `child dir from great-grandchild file`() {
            getRelationship(DiskEntries.dir1, DiskEntries.file111) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `grandchild dir from great-grandchild file`() {
            getRelationship(DiskEntries.dir11, DiskEntries.file111) shouldBe DiskEntry.Relationship.Descendant
        }
    }

    @Nested
    inner class WithSibling {
        @Test
        fun `file from file`() {
            getRelationship(DiskEntries.file111, DiskEntries.file112) shouldBe DiskEntry.Relationship.Sibling
        }

        @Test
        fun `dir from dir`() {
            getRelationship(DiskEntries.dir1, DiskEntries.dir2) shouldBe DiskEntry.Relationship.Sibling
        }

        @Test
        fun `dir from file`() {
            getRelationship(DiskEntries.dir1, DiskEntries.file1) shouldBe DiskEntry.Relationship.Sibling
        }

        @Test
        fun `file from dir`() {
            getRelationship(DiskEntries.file12, DiskEntries.dir11) shouldBe DiskEntry.Relationship.Sibling
        }
    }

    @Nested
    inner class WithUnrelated {
        @Test
        fun `file from file`() {
            getRelationship(DiskEntries.file12, DiskEntries.file21) shouldBe DiskEntry.Relationship.Unrelated
        }

        @Test
        fun `dir from dir`() {
            getRelationship(DiskEntries.dir11, DiskEntries.dir2) shouldBe DiskEntry.Relationship.Unrelated
        }

        @Test
        fun `dir from file`() {
            getRelationship(DiskEntries.dir2, DiskEntries.file111) shouldBe DiskEntry.Relationship.Unrelated
        }

        @Test
        fun `file from dir`() {
            getRelationship(DiskEntries.file12, DiskEntries.dir2) shouldBe DiskEntry.Relationship.Unrelated
        }
    }
}
