package com.diskusage.libraries.screens.navigation

import com.diskusage.libraries.screens.navigation.data.TestRoutes
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test

class ScreensControllerTest {

    private lateinit var screensController: ScreensController

    @Before
    fun setUp() {
        screensController = ScreensControllerImpl(TestRoutes.FirstScreen)
    }

    @Test
    fun `initial routes`() {
        screensController.routes shouldBe listOf(TestRoutes.FirstScreen)
    }

    @Test
    fun `pushing same route multiple times then popping up to it`() {
        screensController.push(TestRoutes.SecondScreen)
        screensController.push(TestRoutes.ThirdScreen)
        screensController.push(TestRoutes.FourthScreen)
        screensController.push(TestRoutes.SecondScreen)
        screensController.push(TestRoutes.FifthScreen)
        screensController.routes shouldBe listOf(
            TestRoutes.FirstScreen,
            TestRoutes.SecondScreen,
            TestRoutes.ThirdScreen,
            TestRoutes.FourthScreen,
            TestRoutes.SecondScreen,
            TestRoutes.FifthScreen
        )
        screensController.pop(TestRoutes.SecondScreen)
        screensController.routes shouldBe listOf(
            TestRoutes.FirstScreen,
            TestRoutes.SecondScreen,
            TestRoutes.ThirdScreen,
            TestRoutes.FourthScreen,
            TestRoutes.SecondScreen
        )
        screensController.pop()
        screensController.pop(TestRoutes.SecondScreen)
        screensController.routes shouldBe listOf(
            TestRoutes.FirstScreen,
            TestRoutes.SecondScreen
        )
        screensController.pop()
        screensController.routes shouldBe listOf(
            TestRoutes.FirstScreen
        )
    }

    @Test
    fun `popping start route`() {
        shouldThrow<IllegalStateException> {
            screensController.pop()
        }
    }

    @Test
    fun `popping route that is not on the stack`() {
        shouldThrow<IllegalArgumentException> {
            screensController.pop(TestRoutes.SecondScreen)
        }
    }

    @Test
    fun `popping to current route`() {
        screensController.push(TestRoutes.SecondScreen)
        shouldThrow<IllegalArgumentException> {
            screensController.pop(TestRoutes.SecondScreen)
        }
    }
}
