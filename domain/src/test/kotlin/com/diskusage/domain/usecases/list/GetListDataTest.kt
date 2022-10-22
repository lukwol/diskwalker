package com.diskusage.domain.usecases.list

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.stubs.DiskEntries
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
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import org.koin.test.junit5.mock.MockProviderExtension
import org.koin.test.mock.declareMock

@OptIn(ExperimentalCoroutinesApi::class)
class GetListDataTest : KoinTest {

    private val getListData by inject<GetListData>()
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

    @Test
    fun file() = runTest {
        val (parentItem, childItems) = getListData(DiskEntries.file111)
        parentItem.diskEntry shouldBe DiskEntries.file111
        childItems shouldHaveSize 0
        verify { sortDiskEntries.invoke(any()) }
    }

    @Test
    fun directory() = runTest {
        val (parentItem, childItems) = getListData(DiskEntries.dir1)
        parentItem.diskEntry shouldBe DiskEntries.dir1
        childItems shouldHaveSize 3
        verify { sortDiskEntries.invoke(any()) }
    }
}
