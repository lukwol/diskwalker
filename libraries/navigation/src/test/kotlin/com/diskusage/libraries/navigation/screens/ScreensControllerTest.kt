package com.diskusage.libraries.navigation.screens

import com.diskusage.libraries.navigation.screens.data.TestRoutes
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test

class ScreensControllerTest {

    private lateinit var screensController: ScreensController

    @Before
    fun setUp() {
        screensController = ScreensControllerImpl(TestRoutes.StartScreen)
    }

    @Test
    fun `initial routes`() {
        screensController.routes shouldBe listOf(TestRoutes.StartScreen)
    }

    @Test
    fun `pushing same route multiple times then popping up to it`() {
        screensController.push(TestRoutes.FirstScreen)
        screensController.push(TestRoutes.SecondScreen)
        screensController.push(TestRoutes.ThirdScreen)
        screensController.push(TestRoutes.FirstScreen)
        screensController.push(TestRoutes.FourthScreen)
        screensController.routes shouldBe listOf(
            TestRoutes.StartScreen,
            TestRoutes.FirstScreen,
            TestRoutes.SecondScreen,
            TestRoutes.ThirdScreen,
            TestRoutes.FirstScreen,
            TestRoutes.FourthScreen
        )
        screensController.pop(TestRoutes.FirstScreen)
        screensController.routes shouldBe listOf(
            TestRoutes.StartScreen,
            TestRoutes.FirstScreen,
            TestRoutes.SecondScreen,
            TestRoutes.ThirdScreen,
            TestRoutes.FirstScreen
        )
        screensController.pop()
        screensController.pop(TestRoutes.FirstScreen)
        screensController.routes shouldBe listOf(
            TestRoutes.StartScreen,
            TestRoutes.FirstScreen
        )
        screensController.pop()
        screensController.routes shouldBe listOf(
            TestRoutes.StartScreen
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
            screensController.pop(TestRoutes.FirstScreen)
        }
    }

    @Test
    fun `popping to current route`() {
        screensController.push(TestRoutes.FirstScreen)
        shouldThrow<IllegalArgumentException> {
            screensController.pop(TestRoutes.FirstScreen)
        }
    }
}
