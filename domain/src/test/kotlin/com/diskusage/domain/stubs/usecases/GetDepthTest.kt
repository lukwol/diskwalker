package com.diskusage.domain.stubs.usecases

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.stubs.mocks.DiskEntryStubs
import com.diskusage.domain.usecases.GetDepth
import io.kotest.matchers.shouldBe
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
    fun `root dir from root dir`() {
        getDepth(DiskEntryStubs.rootDir, DiskEntryStubs.rootDir) shouldBe 1f
    }

    @Test
    fun `dir1 from dir1`() {
        getDepth(DiskEntryStubs.dir1, DiskEntryStubs.dir1) shouldBe 1f
    }

    @Test
    fun `dir11 from dir11`() {
        getDepth(DiskEntryStubs.dir11, DiskEntryStubs.dir11) shouldBe 1f
    }

    @Test
    fun `file112 from file112`() {
        getDepth(DiskEntryStubs.file112, DiskEntryStubs.file112) shouldBe 1f
    }

    @Test
    fun `dir1 from root dir`() {
        getDepth(DiskEntryStubs.dir1, DiskEntryStubs.rootDir) shouldBe 2f
    }

    @Test
    fun `dir2 from root dir`() {
        getDepth(DiskEntryStubs.dir2, DiskEntryStubs.rootDir) shouldBe 2f
    }

    @Test
    fun `dir11 from dir1`() {
        getDepth(DiskEntryStubs.dir11, DiskEntryStubs.dir1) shouldBe 2f
    }

    @Test
    fun `dir11 from root dir`() {
        getDepth(DiskEntryStubs.dir11, DiskEntryStubs.rootDir) shouldBe 3f
    }

    @Test
    fun `file111 from root dir1`() {
        getDepth(DiskEntryStubs.file111, DiskEntryStubs.dir1) shouldBe 3f
    }

    @Test
    fun `file112 from root dir11`() {
        getDepth(DiskEntryStubs.file112, DiskEntryStubs.dir11) shouldBe 2f
    }

    @Test
    fun `file111 from root dir`() {
        getDepth(DiskEntryStubs.file111, DiskEntryStubs.rootDir) shouldBe 4f
    }

    @Test
    fun `root dir from dir1`() {
        getDepth(DiskEntryStubs.rootDir, DiskEntryStubs.dir1) shouldBe 0f
    }

    @Test
    fun `root dir from file`() {
        getDepth(DiskEntryStubs.rootDir, DiskEntryStubs.file1) shouldBe 0f
    }

    @Test
    fun `dir2 from file21`() {
        getDepth(DiskEntryStubs.dir2, DiskEntryStubs.file21) shouldBe 0f
    }

    @Test
    fun `file112 from root dir2`() {
        getDepth(DiskEntryStubs.file112, DiskEntryStubs.dir2) shouldBe 0f
    }

    @Test
    fun `dir1 from dir2`() {
        getDepth(DiskEntryStubs.dir1, DiskEntryStubs.dir2) shouldBe 0f
    }

    @Test
    fun `dir11 from dir2`() {
        getDepth(DiskEntryStubs.dir11, DiskEntryStubs.dir2) shouldBe 0f
    }

    @Test
    fun `root file from root dir`() {
        getDepth(DiskEntryStubs.rootFile, DiskEntryStubs.rootDir) shouldBe 0f
    }

    @Test
    fun `file111 from file112`() {
        getDepth(DiskEntryStubs.file111, DiskEntryStubs.file112) shouldBe 0f
    }
}
