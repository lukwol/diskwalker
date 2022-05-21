package com.diskusage.domain.stubs.usecases

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.stubs.mocks.DiskEntryStubs
import com.diskusage.domain.usecases.GetSweepAngle
import io.kotest.matchers.floats.between
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class GetSweepAngleTest : KoinTest {

    private val getSweepAngle by inject<GetSweepAngle>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
    }

    @Test
    fun `root dir from root dir`() {
        getSweepAngle(DiskEntryStubs.rootDir, DiskEntryStubs.rootDir) shouldBe 360f
    }

    @Test
    fun `dir1 from dir1`() {
        getSweepAngle(DiskEntryStubs.dir1, DiskEntryStubs.dir1) shouldBe 360f
    }

    @Test
    fun `dir11 from dir11`() {
        getSweepAngle(DiskEntryStubs.dir11, DiskEntryStubs.dir11) shouldBe 360f
    }

    @Test
    fun `file112 from file112`() {
        getSweepAngle(DiskEntryStubs.file112, DiskEntryStubs.file112) shouldBe 360f
    }

    @Test
    fun `dir1 from root dir`() {
        getSweepAngle(DiskEntryStubs.dir1, DiskEntryStubs.rootDir) shouldBe between(1f, 359f, 0.5f)
    }

    @Test
    fun `dir2 from root dir`() {
        getSweepAngle(DiskEntryStubs.dir2, DiskEntryStubs.rootDir) shouldBe between(1f, 359f, 0.5f)
    }

    @Test
    fun `dir11 from dir1`() {
        getSweepAngle(DiskEntryStubs.dir11, DiskEntryStubs.dir1) shouldBe between(1f, 359f, 0.5f)
    }

    @Test
    fun `dir11 from root dir`() {
        getSweepAngle(DiskEntryStubs.dir11, DiskEntryStubs.rootDir) shouldBe between(1f, 359f, 0.5f)
    }

    @Test
    fun `file111 from root dir1`() {
        getSweepAngle(DiskEntryStubs.file111, DiskEntryStubs.dir1) shouldBe between(1f, 359f, 0.5f)
    }

    @Test
    fun `file112 from root dir11`() {
        getSweepAngle(DiskEntryStubs.file112, DiskEntryStubs.dir11) shouldBe between(1f, 359f, 0.5f)
    }

    @Test
    fun `file111 from root dir`() {
        getSweepAngle(DiskEntryStubs.file111, DiskEntryStubs.rootDir) shouldBe between(1f, 359f, 0.5f)
    }

    @Test
    fun `root dir from dir1`() {
        getSweepAngle(DiskEntryStubs.rootDir, DiskEntryStubs.dir1) shouldBe 360f
    }

    @Test
    fun `root dir from file`() {
        getSweepAngle(DiskEntryStubs.rootDir, DiskEntryStubs.file1) shouldBe 360f
    }

    @Test
    fun `dir2 from file21`() {
        getSweepAngle(DiskEntryStubs.dir2, DiskEntryStubs.file21) shouldBe 360f
    }

    @Test
    fun `file112 from root dir2`() {
        getSweepAngle(DiskEntryStubs.file112, DiskEntryStubs.dir2) shouldBe 0f
    }

    @Test
    fun `dir1 from dir2`() {
        getSweepAngle(DiskEntryStubs.dir1, DiskEntryStubs.dir2) shouldBe 0f
    }

    @Test
    fun `dir11 from dir2`() {
        getSweepAngle(DiskEntryStubs.dir11, DiskEntryStubs.dir2) shouldBe 0f
    }

    @Test
    fun `root file from root dir`() {
        getSweepAngle(DiskEntryStubs.rootFile, DiskEntryStubs.rootDir) shouldBe 0f
    }

    @Test
    fun `file111 from file112`() {
        getSweepAngle(DiskEntryStubs.file111, DiskEntryStubs.file112) shouldBe 0f
    }
}
