package com.diskusage.data.datasources

import com.diskusage.data.di.dataModule
import com.diskusage.domain.datasources.DiskEntryDataSource
import com.diskusage.domain.entities.DiskEntry
import com.diskusage.support.FileSize
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkClass
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock
import java.nio.file.Path
import kotlin.io.path.absolutePathString

class DiskEntryDataSourceTest : KoinTest {

    private val diskEntryDataSource by inject<DiskEntryDataSource>()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(dataModule)
    }

    @JvmField
    @RegisterExtension
    val mockProvider = MockProviderExtension.create { clazz ->
        mockkClass(clazz)
    }

    private val testDirPath = Path.of("src/test/kotlin/com/diskusage/data/testDir")
    private val subDirPath = Path.of("src/test/kotlin/com/diskusage/data/testDir/subDir")
    private val fooFilePath = Path.of("src/test/kotlin/com/diskusage/data/testDir/foo.txt")
    private val barFilePath = Path.of("src/test/kotlin/com/diskusage/data/testDir/bar.txt")
    private val bazFilePath = Path.of("src/test/kotlin/com/diskusage/data/testDir/subDir/baz.txt")

    private lateinit var testDir: DiskEntry.Directory
    private lateinit var subDir: DiskEntry.Directory
    private lateinit var fooFile: DiskEntry.File
    private lateinit var barFile: DiskEntry.File
    private lateinit var bazFile: DiskEntry.File

    @BeforeEach
    internal fun setUp() {
        declareMock<FileSize> {
            every { sizeOnDisk(fooFilePath.absolutePathString()) } returns 256
            every { sizeOnDisk(barFilePath.absolutePathString()) } returns 1024
            every { sizeOnDisk(bazFilePath.absolutePathString()) } returns 4096
        }

        testDir = diskEntryDataSource.diskEntry(testDirPath) as DiskEntry.Directory
        subDir = testDir.children.find { it is DiskEntry.Directory } as DiskEntry.Directory
        fooFile = testDir.children.find { it.name == "foo.txt" } as DiskEntry.File
        barFile = testDir.children.find { it.name == "bar.txt" } as DiskEntry.File
        bazFile = subDir.children.find { it.name == "baz.txt" } as DiskEntry.File
    }

    @AfterEach
    internal fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test dir`(): Unit = testDir.run {
        name shouldBe "testDir"
        path shouldBe testDirPath
        parent shouldBe null
        hasSizeCalculated shouldBe true
        size shouldBe 256 + 1024 + 4096
        children shouldHaveSize 3
        children.forEach { it.parent shouldBe testDir }
    }

    @Test
    fun `test subdir`(): Unit = subDir.run {
        name shouldBe "subDir"
        path shouldBe subDirPath
        parent shouldBe testDir
        hasSizeCalculated shouldBe true
        size shouldBe 4096
        children shouldHaveSize 1
        children.forEach { it.parent shouldBe subDir }
    }

    @Test
    fun `test foo file`(): Unit = fooFile.run {
        name shouldBe "foo.txt"
        path shouldBe fooFilePath
        parent shouldBe testDir
        hasSizeCalculated shouldBe true
        size shouldBe 256
    }

    @Test
    fun `test bar file`(): Unit = barFile.run {
        name shouldBe "bar.txt"
        path shouldBe barFilePath
        parent shouldBe testDir
        hasSizeCalculated shouldBe true
        size shouldBe 1024
    }

    @Test
    fun `test baz file`(): Unit = bazFile.run {
        name shouldBe "baz.txt"
        path shouldBe bazFilePath
        parent shouldBe subDir
        parent?.parent shouldBe testDir
        hasSizeCalculated shouldBe true
        size shouldBe 4096
    }
}
