package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.stubs.DiskEntryStubs
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension

class GetRootTest : KoinTest {

    private val getRoot by inject<GetRoot>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
    }

    @Test
    fun file() {
        getRoot(DiskEntryStubs.file112) shouldBe DiskEntryStubs.rootDir
        getRoot(DiskEntryStubs.file22) shouldBe DiskEntryStubs.rootDir
    }

    @Test
    fun directory() {
        getRoot(DiskEntryStubs.dir2) shouldBe DiskEntryStubs.rootDir
        getRoot(DiskEntryStubs.dir11) shouldBe DiskEntryStubs.rootDir
    }

    @Test
    fun `root file`() {
        getRoot(DiskEntryStubs.rootFile) shouldBe DiskEntryStubs.rootFile
    }

    @Test
    fun `root directory`() {
        getRoot(DiskEntryStubs.rootDir) shouldBe DiskEntryStubs.rootDir
    }
}
