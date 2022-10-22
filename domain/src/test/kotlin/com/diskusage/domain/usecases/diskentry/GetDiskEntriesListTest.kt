package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.di.domainModule
import com.diskusage.domain.stubs.DiskEntries
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
                every { this@declareMock(not(DiskEntries.dir11), any()) } returns true
                every { this@declareMock(DiskEntries.dir11, any()) } returns false
            }
        }

        @Test
        fun `disk entries list`() {
            getDiskEntriesList(DiskEntries.rootDir) shouldContainExactlyInAnyOrder listOf(
                DiskEntries.rootDir,
                DiskEntries.file1,
                DiskEntries.dir1,
                DiskEntries.file11,
                DiskEntries.file12,
                DiskEntries.dir2,
                DiskEntries.file21,
                DiskEntries.file22
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
            getDiskEntriesList(DiskEntries.rootDir) shouldContainExactlyInAnyOrder listOf(
                DiskEntries.rootDir,
                DiskEntries.file1,
                DiskEntries.dir1,
                DiskEntries.file11,
                DiskEntries.file12,
                DiskEntries.dir11,
                DiskEntries.file111,
                DiskEntries.file112,
                DiskEntries.dir2,
                DiskEntries.file21,
                DiskEntries.file22
            )
        }
    }
}
