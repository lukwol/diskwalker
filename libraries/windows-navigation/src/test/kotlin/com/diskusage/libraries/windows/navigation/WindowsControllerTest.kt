package com.diskusage.libraries.windows.navigation

import com.diskusage.libraries.windows.navigation.data.TestRoutes
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test

class WindowsControllerTest {

    private lateinit var windowsController: WindowsController

    @Before
    fun setUp() {
        windowsController = WindowsControllerImpl(TestRoutes.StartWindow)
    }

    @Test
    fun `initial routes`() {
        windowsController.routes shouldBe listOf(TestRoutes.StartWindow)
    }

    @Test
    fun `opening and closing multiple windows`() {
        windowsController.open(TestRoutes.FirstWindow)
        windowsController.routes shouldBe setOf(
            TestRoutes.StartWindow,
            TestRoutes.FirstWindow
        )

        windowsController.open(TestRoutes.SecondWindow)
        windowsController.routes shouldBe setOf(
            TestRoutes.StartWindow,
            TestRoutes.FirstWindow,
            TestRoutes.SecondWindow
        )

        windowsController.close(TestRoutes.StartWindow)
        windowsController.routes shouldBe setOf(
            TestRoutes.FirstWindow,
            TestRoutes.SecondWindow
        )

        windowsController.close(TestRoutes.FirstWindow)
        windowsController.routes shouldBe setOf(TestRoutes.SecondWindow)
    }

    @Test
    fun `opening window that is already opened`() {
        windowsController.open(TestRoutes.FirstWindow)
        shouldThrow<IllegalArgumentException> {
            windowsController.open(TestRoutes.FirstWindow)
        }
    }

    @Test
    fun `closing window that is not opened`() {
        shouldThrow<IllegalArgumentException> {
            windowsController.close(TestRoutes.FirstWindow)
        }
    }
}
