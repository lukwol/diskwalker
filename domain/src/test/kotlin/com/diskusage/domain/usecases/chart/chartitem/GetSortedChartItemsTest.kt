package com.diskusage.domain.usecases.chart.chartitem

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.stubs.DiskEntryStubs
import com.diskusage.domain.usecases.chart.SortDiskEntries
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
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

class GetSortedChartItemsTest : KoinTest {

    private val getSortedChartItems by inject<GetSortedChartItems>()
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

    @Nested
    inner class StaticChartItems {
        @Test
        fun `simple file`() {
            getSortedChartItems(DiskEntryStubs.rootFile) shouldHaveSize 1
            verify { sortDiskEntries.invoke(any()) }
        }

        @Test
        fun `simple directory`() {
            getSortedChartItems(DiskEntryStubs.dir11) shouldHaveSize 3
            verify { sortDiskEntries.invoke(any()) }
        }

        @Test
        fun `root directory`() {
            getSortedChartItems(DiskEntryStubs.rootDir) shouldHaveSize 11
            verify { sortDiskEntries.invoke(any()) }
        }
    }

    @Nested
    inner class ChartItemsForTransition {

        @Nested
        inner class FromSame {
            @Test
            fun `file from file without parent`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.rootFile, DiskEntryStubs.rootFile)
                fromItems shouldHaveSize 1
                toItems shouldHaveSize 1
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `file from file with parent`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.file1, DiskEntryStubs.file1)
                fromItems shouldHaveSize 1
                toItems shouldHaveSize 1
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `dir from dir without parent`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.rootDir, DiskEntryStubs.rootDir)
                fromItems shouldHaveSize 11
                toItems shouldHaveSize 11
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `dir from dir with parent`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.dir1, DiskEntryStubs.dir1)
                fromItems shouldHaveSize 6
                toItems shouldHaveSize 6
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }
        }

        @Nested
        inner class FromAncestor {
            @Test
            fun `child file from root dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.file1, DiskEntryStubs.rootDir)
                fromItems shouldHaveSize 11
                toItems shouldHaveSize 11
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `grandchild file from root dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.file11, DiskEntryStubs.rootDir)
                fromItems shouldHaveSize 11
                toItems shouldHaveSize 11
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `great-grandchild file from root dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.file112, DiskEntryStubs.rootDir)
                fromItems shouldHaveSize 11
                toItems shouldHaveSize 11
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `child dir from root dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.dir2, DiskEntryStubs.rootDir)
                fromItems shouldHaveSize 11
                toItems shouldHaveSize 11
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `grandchild dir from root dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.dir11, DiskEntryStubs.rootDir)
                fromItems shouldHaveSize 11
                toItems shouldHaveSize 11
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `grandchild dir from child dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.dir11, DiskEntryStubs.dir1)
                fromItems shouldHaveSize 6
                toItems shouldHaveSize 6
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `grandchild file from child dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.file11, DiskEntryStubs.dir1)
                fromItems shouldHaveSize 6
                toItems shouldHaveSize 6
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `great-grandchild file from child dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.file111, DiskEntryStubs.dir1)
                fromItems shouldHaveSize 6
                toItems shouldHaveSize 6
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `great-grandchild file from grandchild dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.file111, DiskEntryStubs.dir11)
                fromItems shouldHaveSize 3
                toItems shouldHaveSize 3
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }
        }

        @Nested
        inner class FromDescendant {
            @Test
            fun `root dir from child file`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.rootDir, DiskEntryStubs.file1)
                fromItems shouldHaveSize 11
                toItems shouldHaveSize 11
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `root dir from grandchild file`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.rootDir, DiskEntryStubs.file11)
                fromItems shouldHaveSize 11
                toItems shouldHaveSize 11
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `root dir from great-grandchild file`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.rootDir, DiskEntryStubs.file112)
                fromItems shouldHaveSize 11
                toItems shouldHaveSize 11
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `root dir from child dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.rootDir, DiskEntryStubs.dir2)
                fromItems shouldHaveSize 11
                toItems shouldHaveSize 11
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `root dir from grandchild dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.rootDir, DiskEntryStubs.dir11)
                fromItems shouldHaveSize 11
                toItems shouldHaveSize 11
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `child dir from grandchild dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.dir1, DiskEntryStubs.dir11)
                fromItems shouldHaveSize 6
                toItems shouldHaveSize 6
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `child dir from grandchild file`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.dir1, DiskEntryStubs.file11)
                fromItems shouldHaveSize 6
                toItems shouldHaveSize 6
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `child dir from great-grandchild file`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.dir1, DiskEntryStubs.file111)
                fromItems shouldHaveSize 6
                toItems shouldHaveSize 6
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `grandchild dir from great-grandchild file`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.dir11, DiskEntryStubs.file111)
                fromItems shouldHaveSize 3
                toItems shouldHaveSize 3
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }
        }

        @Nested
        inner class FromSibling {
            @Test
            fun `file from file`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.file111, DiskEntryStubs.file112)
                fromItems shouldHaveSize 2
                toItems shouldHaveSize 2
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `dir from dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.dir1, DiskEntryStubs.dir2)
                fromItems shouldHaveSize 9
                toItems shouldHaveSize 9
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `dir from file`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.dir1, DiskEntryStubs.file1)
                fromItems shouldHaveSize 7
                toItems shouldHaveSize 7
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `file from dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.file12, DiskEntryStubs.dir11)
                fromItems shouldHaveSize 4
                toItems shouldHaveSize 4
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }
        }

        @Nested
        inner class FromUnrelated {
            @Test
            fun `file from file`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.file12, DiskEntryStubs.file21)
                fromItems shouldHaveSize 2
                toItems shouldHaveSize 2
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `dir from dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.dir11, DiskEntryStubs.dir2)
                fromItems shouldHaveSize 6
                toItems shouldHaveSize 6
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `dir from file`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.dir2, DiskEntryStubs.file111)
                fromItems shouldHaveSize 4
                toItems shouldHaveSize 4
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }

            @Test
            fun `file from dir`() {
                val (fromItems, toItems) = getSortedChartItems(DiskEntryStubs.file12, DiskEntryStubs.dir2)
                fromItems shouldHaveSize 4
                toItems shouldHaveSize 4
                fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
                verify { sortDiskEntries.invoke(any()) }
            }
        }
    }
}
