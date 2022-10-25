package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.data.TestDiskEntries
import com.diskusage.domain.di.domainModule
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
        getRoot(TestDiskEntries.file112) shouldBe TestDiskEntries.rootDir
        getRoot(TestDiskEntries.file22) shouldBe TestDiskEntries.rootDir
    }

    @Test
    fun directory() {
        getRoot(TestDiskEntries.dir2) shouldBe TestDiskEntries.rootDir
        getRoot(TestDiskEntries.dir11) shouldBe TestDiskEntries.rootDir
    }

    @Test
    fun `root file`() {
        getRoot(TestDiskEntries.rootFile) shouldBe TestDiskEntries.rootFile
    }

    @Test
    fun `root directory`() {
        getRoot(TestDiskEntries.rootDir) shouldBe TestDiskEntries.rootDir
    }
}
