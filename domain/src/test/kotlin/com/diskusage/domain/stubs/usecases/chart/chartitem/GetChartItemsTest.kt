package com.diskusage.domain.stubs.usecases.chart.chartitem

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.entities.ChartItem
import com.diskusage.domain.stubs.stubs.DiskEntryStubs
import com.diskusage.domain.usecases.chart.SortDiskEntries
import com.diskusage.domain.usecases.chart.chartitem.GetChartItems
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

class GetChartItemsTest : KoinTest {

    private val getChartItems by inject<GetChartItems>()
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
    internal fun setUp() {
        sortDiskEntries = declareMock {
            every { this@declareMock(any()) } answers { callOriginal() }
        }
    }

    @AfterEach
    internal fun tearDown() {
        clearAllMocks()
    }

    @Nested
    inner class StaticChartItems {
        @Test
        internal fun `simple file`() {
            getChartItems(DiskEntryStubs.rootFile) shouldHaveSize 1
            verify { sortDiskEntries.invoke(any()) }
        }

        @Test
        internal fun `simple directory`() {
            getChartItems(DiskEntryStubs.dir11) shouldHaveSize 3
            verify { sortDiskEntries.invoke(any()) }
        }

        @Test
        internal fun `root directory`() {
            getChartItems(DiskEntryStubs.rootDir) shouldHaveSize 11
            verify { sortDiskEntries.invoke(any()) }
        }
    }

    @Nested
    inner class ChartItemsForTransition {

        @Nested
        inner class FromSame {
            @Test
            fun `file from file without parent`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.rootFile, DiskEntryStubs.rootFile)
                compareChartItems(fromItems, toItems, 1)
            }

            @Test
            fun `file from file with parent`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.file1, DiskEntryStubs.file1)
                compareChartItems(fromItems, toItems, 1)
            }

            @Test
            fun `dir from dir without parent`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.rootDir, DiskEntryStubs.rootDir)
                compareChartItems(fromItems, toItems, 11)
            }

            @Test
            fun `dir from dir with parent`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.dir1, DiskEntryStubs.dir1)
                compareChartItems(fromItems, toItems, 6)
            }
        }

        @Nested
        inner class FromAncestor {
            @Test
            fun `child file from root dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.file1, DiskEntryStubs.rootDir)
                compareChartItems(fromItems, toItems, 11)
            }

            @Test
            fun `grandchild file from root dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.file11, DiskEntryStubs.rootDir)
                compareChartItems(fromItems, toItems, 11)
            }

            @Test
            fun `great-grandchild file from root dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.file112, DiskEntryStubs.rootDir)
                compareChartItems(fromItems, toItems, 11)
            }

            @Test
            fun `child dir from root dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.dir2, DiskEntryStubs.rootDir)
                compareChartItems(fromItems, toItems, 11)
            }

            @Test
            fun `grandchild dir from root dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.dir11, DiskEntryStubs.rootDir)
                compareChartItems(fromItems, toItems, 11)
            }

            @Test
            fun `grandchild dir from child dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.dir11, DiskEntryStubs.dir1)
                compareChartItems(fromItems, toItems, 6)
            }

            @Test
            fun `grandchild file from child dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.file11, DiskEntryStubs.dir1)
                compareChartItems(fromItems, toItems, 6)
            }

            @Test
            fun `great-grandchild file from child dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.file111, DiskEntryStubs.dir1)
                compareChartItems(fromItems, toItems, 6)
            }

            @Test
            fun `great-grandchild file from grandchild dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.file111, DiskEntryStubs.dir11)
                compareChartItems(fromItems, toItems, 3)
            }
        }

        @Nested
        inner class FromDescendant {
            @Test
            fun `root dir from child file`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.rootDir, DiskEntryStubs.file1)
                compareChartItems(fromItems, toItems, 11)
            }

            @Test
            fun `root dir from grandchild file`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.rootDir, DiskEntryStubs.file11)
                compareChartItems(fromItems, toItems, 11)
            }

            @Test
            fun `root dir from great-grandchild file`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.rootDir, DiskEntryStubs.file112)
                compareChartItems(fromItems, toItems, 11)
            }

            @Test
            fun `root dir from child dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.rootDir, DiskEntryStubs.dir2)
                compareChartItems(fromItems, toItems, 11)
            }

            @Test
            fun `root dir from grandchild dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.rootDir, DiskEntryStubs.dir11)
                compareChartItems(fromItems, toItems, 11)
            }

            @Test
            fun `child dir from grandchild dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.dir1, DiskEntryStubs.dir11)
                compareChartItems(fromItems, toItems, 6)
            }

            @Test
            fun `child dir from grandchild file`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.dir1, DiskEntryStubs.file11)
                compareChartItems(fromItems, toItems, 6)
            }

            @Test
            fun `child dir from great-grandchild file`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.dir1, DiskEntryStubs.file111)
                compareChartItems(fromItems, toItems, 6)
            }

            @Test
            fun `grandchild dir from great-grandchild file`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.dir11, DiskEntryStubs.file111)
                compareChartItems(fromItems, toItems, 3)
            }
        }

        @Nested
        inner class FromSibling {
            @Test
            fun `file from file`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.file111, DiskEntryStubs.file112)
                compareChartItems(fromItems, toItems, 2)
            }

            @Test
            fun `dir from dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.dir1, DiskEntryStubs.dir2)
                compareChartItems(fromItems, toItems, 9)
            }

            @Test
            fun `dir from file`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.dir1, DiskEntryStubs.file1)
                compareChartItems(fromItems, toItems, 7)
            }

            @Test
            fun `file from dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.file12, DiskEntryStubs.dir11)
                compareChartItems(fromItems, toItems, 4)
            }
        }

        @Nested
        inner class FromUnrelated {
            @Test
            fun `file from file`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.file12, DiskEntryStubs.file21)
                compareChartItems(fromItems, toItems, 2)
            }

            @Test
            fun `dir from dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.dir11, DiskEntryStubs.dir2)
                compareChartItems(fromItems, toItems, 6)
            }

            @Test
            fun `dir from file`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.dir2, DiskEntryStubs.file111)
                compareChartItems(fromItems, toItems, 4)
            }

            @Test
            fun `file from dir`() {
                val (fromItems, toItems) = getChartItems(DiskEntryStubs.file12, DiskEntryStubs.dir2)
                compareChartItems(fromItems, toItems, 4)
            }
        }

        private fun compareChartItems(fromItems: List<ChartItem>, toItems: List<ChartItem>, expectedSize: Int) {
            fromItems shouldHaveSize expectedSize
            toItems shouldHaveSize expectedSize
            fromItems.map(ChartItem::diskEntry) shouldBe toItems.map(ChartItem::diskEntry)
            verify { sortDiskEntries.invoke(any()) }
        }
    }
}
