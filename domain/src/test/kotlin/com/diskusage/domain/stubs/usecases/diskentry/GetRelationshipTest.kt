package com.diskusage.domain.stubs.usecases.diskentry

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.domain.stubs.stubs.DiskEntryStubs
import com.diskusage.domain.usecases.diskentry.GetRelationship
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
            getRelationship(DiskEntryStubs.rootFile, DiskEntryStubs.rootFile) shouldBe DiskEntry.Relationship.Identity
        }

        @Test
        fun `file from file with parent`() {
            getRelationship(DiskEntryStubs.file1, DiskEntryStubs.file1) shouldBe DiskEntry.Relationship.Identity
        }

        @Test
        fun `dir from dir without parent`() {
            getRelationship(DiskEntryStubs.rootDir, DiskEntryStubs.rootDir) shouldBe DiskEntry.Relationship.Identity
        }

        @Test
        fun `dir from dir with parent`() {
            getRelationship(DiskEntryStubs.dir1, DiskEntryStubs.dir1) shouldBe DiskEntry.Relationship.Identity
        }
    }

    @Nested
    inner class WithAncestor {
        @Test
        fun `child file from root dir`() {
            getRelationship(DiskEntryStubs.file1, DiskEntryStubs.rootDir) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `grandchild file from root dir`() {
            getRelationship(DiskEntryStubs.file11, DiskEntryStubs.rootDir) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `great-grandchild file from root dir`() {
            getRelationship(DiskEntryStubs.file112, DiskEntryStubs.rootDir) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `child dir from root dir`() {
            getRelationship(DiskEntryStubs.dir2, DiskEntryStubs.rootDir) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `grandchild dir from root dir`() {
            getRelationship(DiskEntryStubs.dir11, DiskEntryStubs.rootDir) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `grandchild dir from child dir`() {
            getRelationship(DiskEntryStubs.dir11, DiskEntryStubs.dir1) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `grandchild file from child dir`() {
            getRelationship(DiskEntryStubs.file11, DiskEntryStubs.dir1) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `great-grandchild file from child dir`() {
            getRelationship(DiskEntryStubs.file111, DiskEntryStubs.dir1) shouldBe DiskEntry.Relationship.Ancestor
        }

        @Test
        fun `great-grandchild file from grandchild dir`() {
            getRelationship(DiskEntryStubs.file111, DiskEntryStubs.dir11) shouldBe DiskEntry.Relationship.Ancestor
        }
    }

    @Nested
    inner class WithDescendant {

        @Test
        fun `root dir from child file`() {
            getRelationship(DiskEntryStubs.rootDir, DiskEntryStubs.file1) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `root dir from grandchild file`() {
            getRelationship(DiskEntryStubs.rootDir, DiskEntryStubs.file11) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `root dir from great-grandchild file`() {
            getRelationship(DiskEntryStubs.rootDir, DiskEntryStubs.file112) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `root dir from child dir`() {
            getRelationship(DiskEntryStubs.rootDir, DiskEntryStubs.dir2) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `root dir from grandchild dir`() {
            getRelationship(DiskEntryStubs.rootDir, DiskEntryStubs.dir11) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `child dir from grandchild dir`() {
            getRelationship(DiskEntryStubs.dir1, DiskEntryStubs.dir11) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `child dir from grandchild file`() {
            getRelationship(DiskEntryStubs.dir1, DiskEntryStubs.file11) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `child dir from great-grandchild file`() {
            getRelationship(DiskEntryStubs.dir1, DiskEntryStubs.file111) shouldBe DiskEntry.Relationship.Descendant
        }

        @Test
        fun `grandchild dir from great-grandchild file`() {
            getRelationship(DiskEntryStubs.dir11, DiskEntryStubs.file111) shouldBe DiskEntry.Relationship.Descendant
        }
    }

    @Nested
    inner class WithSibling {
        @Test
        fun `file from file`() {
            getRelationship(DiskEntryStubs.file111, DiskEntryStubs.file112) shouldBe DiskEntry.Relationship.Sibling
        }

        @Test
        fun `dir from dir`() {
            getRelationship(DiskEntryStubs.dir1, DiskEntryStubs.dir2) shouldBe DiskEntry.Relationship.Sibling
        }

        @Test
        fun `dir from file`() {
            getRelationship(DiskEntryStubs.dir1, DiskEntryStubs.file1) shouldBe DiskEntry.Relationship.Sibling
        }

        @Test
        fun `file from dir`() {
            getRelationship(DiskEntryStubs.file12, DiskEntryStubs.dir11) shouldBe DiskEntry.Relationship.Sibling
        }
    }

    @Nested
    inner class WithUnrelated {
        @Test
        fun `file from file`() {
            getRelationship(DiskEntryStubs.file12, DiskEntryStubs.file21) shouldBe DiskEntry.Relationship.Unrelated
        }

        @Test
        fun `dir from dir`() {
            getRelationship(DiskEntryStubs.dir11, DiskEntryStubs.dir2) shouldBe DiskEntry.Relationship.Unrelated
        }

        @Test
        fun `dir from file`() {
            getRelationship(DiskEntryStubs.dir2, DiskEntryStubs.file111) shouldBe DiskEntry.Relationship.Unrelated
        }

        @Test
        fun `file from dir`() {
            getRelationship(DiskEntryStubs.file12, DiskEntryStubs.dir2) shouldBe DiskEntry.Relationship.Unrelated
        }
    }
}
