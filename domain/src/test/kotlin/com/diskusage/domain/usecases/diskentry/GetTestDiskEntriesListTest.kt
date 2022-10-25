package com.diskusage.domain.usecases.diskentry

import com.diskusage.domain.data.TestDiskEntries
import com.diskusage.domain.di.domainModule
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

class GetTestDiskEntriesListTest : KoinTest {

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
                every { this@declareMock(not(TestDiskEntries.dir11), any()) } returns true
                every { this@declareMock(TestDiskEntries.dir11, any()) } returns false
            }
        }

        @Test
        fun `disk entries list`() {
            getDiskEntriesList(TestDiskEntries.rootDir) shouldContainExactlyInAnyOrder listOf(
                TestDiskEntries.rootDir,
                TestDiskEntries.file1,
                TestDiskEntries.dir1,
                TestDiskEntries.file11,
                TestDiskEntries.file12,
                TestDiskEntries.dir2,
                TestDiskEntries.file21,
                TestDiskEntries.file22
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
            getDiskEntriesList(TestDiskEntries.rootDir) shouldContainExactlyInAnyOrder listOf(
                TestDiskEntries.rootDir,
                TestDiskEntries.file1,
                TestDiskEntries.dir1,
                TestDiskEntries.file11,
                TestDiskEntries.file12,
                TestDiskEntries.dir11,
                TestDiskEntries.file111,
                TestDiskEntries.file112,
                TestDiskEntries.dir2,
                TestDiskEntries.file21,
                TestDiskEntries.file22
            )
        }
    }
}
