package com.diskusage.libraries.windows.navigation

import com.diskusage.libraries.windows.navigation.data.TestRoutes
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import org.junit.Before
import org.junit.Test

class WindowsMapBuilderTest {

    private lateinit var windowsMapBuilder: WindowsMapBuilder

    @Before
    fun setUp() {
        windowsMapBuilder = WindowsMapBuilder()
    }

    @Test
    fun `windows with unique routes`() {
        with(windowsMapBuilder) {
            window(route = TestRoutes.FirstWindow, content = {})
            window(route = TestRoutes.SecondWindow, content = {})
            build().keys shouldContainExactlyInAnyOrder listOf(TestRoutes.FirstWindow, TestRoutes.SecondWindow)
        }
    }

    @Test
    fun `windows with not unique routes`() {
        with(windowsMapBuilder) {
            window(route = TestRoutes.FirstWindow, content = {})
            window(route = TestRoutes.SecondWindow, content = {})
            shouldThrow<IllegalArgumentException> {
                window(route = TestRoutes.FirstWindow, content = {})
            }
        }
    }
}
