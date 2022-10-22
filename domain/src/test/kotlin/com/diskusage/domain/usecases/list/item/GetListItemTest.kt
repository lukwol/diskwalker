package com.diskusage.domain.usecases.list.item

import com.diskusage.domain.common.Constants
import com.diskusage.domain.di.domainModule
import com.diskusage.domain.model.ListItem
import com.diskusage.domain.stubs.Arcs
import com.diskusage.domain.stubs.DiskEntries
import com.diskusage.domain.usecases.chart.item.arc.GetArc
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

class GetListItemTest : KoinTest {

    private val getListItem by inject<GetListItem>()
    private lateinit var getArc: GetArc

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
        getArc = declareMock {
            every { this@declareMock(DiskEntries.file11, DiskEntries.rootDir) } answers { Arcs.child1 }
            every { this@declareMock(DiskEntries.file12, DiskEntries.dir1) } answers { Arcs.child2 }
        }
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when from item is not passed`() {
        getListItem(
            diskEntry = DiskEntries.file11
        ) shouldBe ListItem(
            diskEntry = DiskEntries.file11,
            color = Constants.Chart.FileColor
        )
    }

    @Test
    fun `when from item is passed`() {
        getListItem(
            diskEntry = DiskEntries.file12,
            fromDiskEntry = DiskEntries.dir1
        ) shouldBe ListItem(
            diskEntry = DiskEntries.file12,
            color = Constants.Chart.FileColor
        )
    }
}
