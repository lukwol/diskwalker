package com.diskusage.domain.usecases.list.item

import com.diskusage.domain.common.Constants
import com.diskusage.domain.data.TestArcs
import com.diskusage.domain.data.TestDiskEntries
import com.diskusage.domain.di.domainModule
import com.diskusage.domain.model.ListItem
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
            every { this@declareMock(TestDiskEntries.file11, TestDiskEntries.rootDir) } answers { TestArcs.child1 }
            every { this@declareMock(TestDiskEntries.file12, TestDiskEntries.dir1) } answers { TestArcs.child2 }
        }
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when from item is not passed`() {
        getListItem(
            diskEntry = TestDiskEntries.file11
        ) shouldBe ListItem(
            diskEntry = TestDiskEntries.file11,
            color = Constants.Chart.FileColor
        )
    }

    @Test
    fun `when from item is passed`() {
        getListItem(
            diskEntry = TestDiskEntries.file12,
            fromDiskEntry = TestDiskEntries.dir1
        ) shouldBe ListItem(
            diskEntry = TestDiskEntries.file12,
            color = Constants.Chart.FileColor
        )
    }
}
