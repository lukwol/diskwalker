package com.diskusage.domain.usecases.chart

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.model.ChartItem
import com.diskusage.domain.stubs.DiskEntryStubs
import com.diskusage.domain.usecases.diskentry.SortDiskEntries
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock

class GetChartDataTest : KoinTest {

    private val getChartData by inject<GetChartData>()
    private lateinit var sortDiskEntries: SortDiskEntries

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(domainModule)
    }

    @JvmField
    @RegisterExtension
    val mockProvider = MockProviderExtension.create { clazz ->
        mockkClass(clazz)
    }

    @BeforeEach
    fun setUp() {
        sortDiskEntries = declareMock {
            every { this@declareMock(any()) } answers { callOriginal() }
        }
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Nested
    inner class StaticChartItems {
        @Test
        fun `simple file`() = runTest {
            val (startItems, endItems) = getChartData(DiskEntryStubs.rootFile)
            startItems shouldHaveSize 1
            endItems shouldBe null
            verify { sortDiskEntries.invoke(any()) }
        }

        @Test
        fun `simple directory`() = runTest {
            val (startItems, endItems) = getChartData(DiskEntryStubs.dir11)
            startItems shouldHaveSize 3
            endItems shouldBe null
            verify { sortDiskEntries.invoke(any()) }
        }

        @Test
        fun `root directory`() = runTest {
            val (startItems, endItems) = getChartData(DiskEntryStubs.rootDir)
            startItems shouldHaveSize 11
            endItems shouldBe null
            verify { sortDiskEntries.invoke(any()) }
        }
    }

    @Nested
    inner class ChartItemsForTransition {

        @OptIn(ExperimentalCoroutinesApi::class)
        @Nested
        inner class FromSame {
            @Test
            fun `file from file without parent`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.rootFile, DiskEntryStubs.rootFile)
                startItems shouldHaveSize 1
                endItems!! shouldHaveSize 1
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `file from file with parent`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.file1, DiskEntryStubs.file1)
                startItems shouldHaveSize 1
                endItems!! shouldHaveSize 1
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `dir from dir without parent`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.rootDir, DiskEntryStubs.rootDir)
                startItems shouldHaveSize 11
                endItems!! shouldHaveSize 11
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `dir from dir with parent`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.dir1, DiskEntryStubs.dir1)
                startItems shouldHaveSize 6
                endItems!! shouldHaveSize 6
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Nested
        inner class FromAncestor {
            @Test
            fun `child file from root dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.file1, DiskEntryStubs.rootDir)
                startItems shouldHaveSize 11
                endItems!! shouldHaveSize 11
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `grandchild file from root dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.file11, DiskEntryStubs.rootDir)
                startItems shouldHaveSize 11
                endItems!! shouldHaveSize 11
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `great-grandchild file from root dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.file112, DiskEntryStubs.rootDir)
                startItems shouldHaveSize 11
                endItems!! shouldHaveSize 11
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `child dir from root dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.dir2, DiskEntryStubs.rootDir)
                startItems shouldHaveSize 11
                endItems!! shouldHaveSize 11
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `grandchild dir from root dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.dir11, DiskEntryStubs.rootDir)
                startItems shouldHaveSize 11
                endItems!! shouldHaveSize 11
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `grandchild dir from child dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.dir11, DiskEntryStubs.dir1)
                startItems shouldHaveSize 6
                endItems!! shouldHaveSize 6
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `grandchild file from child dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.file11, DiskEntryStubs.dir1)
                startItems shouldHaveSize 6
                endItems!! shouldHaveSize 6
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `great-grandchild file from child dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.file111, DiskEntryStubs.dir1)
                startItems shouldHaveSize 6
                endItems!! shouldHaveSize 6
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `great-grandchild file from grandchild dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.file111, DiskEntryStubs.dir11)
                startItems shouldHaveSize 3
                endItems!! shouldHaveSize 3
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Nested
        inner class FromDescendant {
            @Test
            fun `root dir from child file`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.rootDir, DiskEntryStubs.file1)
                startItems shouldHaveSize 11
                endItems!! shouldHaveSize 11
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `root dir from grandchild file`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.rootDir, DiskEntryStubs.file11)
                startItems shouldHaveSize 11
                endItems!! shouldHaveSize 11
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `root dir from great-grandchild file`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.rootDir, DiskEntryStubs.file112)
                startItems shouldHaveSize 11
                endItems!! shouldHaveSize 11
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `root dir from child dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.rootDir, DiskEntryStubs.dir2)
                startItems shouldHaveSize 11
                endItems!! shouldHaveSize 11
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `root dir from grandchild dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.rootDir, DiskEntryStubs.dir11)
                startItems shouldHaveSize 11
                endItems!! shouldHaveSize 11
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `child dir from grandchild dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.dir1, DiskEntryStubs.dir11)
                startItems shouldHaveSize 6
                endItems!! shouldHaveSize 6
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `child dir from grandchild file`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.dir1, DiskEntryStubs.file11)
                startItems shouldHaveSize 6
                endItems!! shouldHaveSize 6
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `child dir from great-grandchild file`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.dir1, DiskEntryStubs.file111)
                startItems shouldHaveSize 6
                endItems!! shouldHaveSize 6
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `grandchild dir from great-grandchild file`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.dir11, DiskEntryStubs.file111)
                startItems shouldHaveSize 3
                endItems!! shouldHaveSize 3
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Nested
        inner class FromSibling {
            @Test
            fun `file from file`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.file111, DiskEntryStubs.file112)
                startItems shouldHaveSize 2
                endItems!! shouldHaveSize 2
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `dir from dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.dir1, DiskEntryStubs.dir2)
                startItems shouldHaveSize 9
                endItems!! shouldHaveSize 9
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `dir from file`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.dir1, DiskEntryStubs.file1)
                startItems shouldHaveSize 7
                endItems!! shouldHaveSize 7
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `file from dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.file12, DiskEntryStubs.dir11)
                startItems shouldHaveSize 4
                endItems!! shouldHaveSize 4
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Nested
        inner class FromUnrelated {
            @Test
            fun `file from file`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.file12, DiskEntryStubs.file21)
                startItems shouldHaveSize 2
                endItems!! shouldHaveSize 2
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `dir from dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.dir11, DiskEntryStubs.dir2)
                startItems shouldHaveSize 6
                endItems!! shouldHaveSize 6
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `dir from file`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.dir2, DiskEntryStubs.file111)
                startItems shouldHaveSize 4
                endItems!! shouldHaveSize 4
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `file from dir`() = runTest {
                val (startItems, endItems) = getChartData(DiskEntryStubs.file12, DiskEntryStubs.dir2)
                startItems shouldHaveSize 4
                endItems!! shouldHaveSize 4
                startItems.map(ChartItem::diskEntry) shouldBe endItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }
        }
    }
}
