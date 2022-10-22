package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.stubs.DiskEntries
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
        getRoot(DiskEntries.file112) shouldBe DiskEntries.rootDir
        getRoot(DiskEntries.file22) shouldBe DiskEntries.rootDir
    }

    @Test
    fun directory() {
        getRoot(DiskEntries.dir2) shouldBe DiskEntries.rootDir
        getRoot(DiskEntries.dir11) shouldBe DiskEntries.rootDir
    }

    @Test
    fun `root file`() {
        getRoot(DiskEntries.rootFile) shouldBe DiskEntries.rootFile
    }

    @Test
    fun `root directory`() {
        getRoot(DiskEntries.rootDir) shouldBe DiskEntries.rootDir
    }
}
