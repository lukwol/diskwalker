package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.stubs.DiskEntryStubs
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkClass
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

class GetDiskEntriesListTest : KoinTest {

    private val getDiskEntriesList by inject<GetDiskEntriesList>()

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

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Nested
    inner class WithExcludedItems {

        @BeforeEach
        fun setUp() {
            declareMock<IncludeDiskEntry> {
                every { this@declareMock(not(DiskEntryStubs.dir11), any()) } returns true
                every { this@declareMock(DiskEntryStubs.dir11, any()) } returns false
            }
        }

        @Test
        fun `disk entries list`() {
            getDiskEntriesList(DiskEntryStubs.rootDir) shouldContainExactlyInAnyOrder listOf(
                DiskEntryStubs.rootDir,
                DiskEntryStubs.file1,
                DiskEntryStubs.dir1,
                DiskEntryStubs.file11,
                DiskEntryStubs.file12,
                DiskEntryStubs.dir2,
                DiskEntryStubs.file21,
                DiskEntryStubs.file22
            )
        }
    }

    @Nested
    inner class WithoutExcludedItems {

        @BeforeEach
        fun setUp() {
            declareMock<IncludeDiskEntry> {
                every { this@declareMock(any(), any()) } returns true
            }
        }

        @Test
        fun `disk entries list`() {
            getDiskEntriesList(DiskEntryStubs.rootDir) shouldContainExactlyInAnyOrder listOf(
                DiskEntryStubs.rootDir,
                DiskEntryStubs.file1,
                DiskEntryStubs.dir1,
                DiskEntryStubs.file11,
                DiskEntryStubs.file12,
                DiskEntryStubs.dir11,
                DiskEntryStubs.file111,
                DiskEntryStubs.file112,
                DiskEntryStubs.dir2,
                DiskEntryStubs.file21,
                DiskEntryStubs.file22
            )
        }
    }
}
