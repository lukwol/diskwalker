package com.diskusage.libraries.screens.navigation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.diskusage.libraries.screens.navigation.components.ScreenNavigation
import com.diskusage.libraries.screens.navigation.data.Arguments
import com.diskusage.libraries.screens.navigation.data.ControlsTexts
import com.diskusage.libraries.screens.navigation.data.TestRoutes
import io.kotest.assertions.throwables.shouldThrow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ScreensNavigationTest {
    @get:Rule
    val compose = createComposeRule()

    @Before
    fun setUp() = compose.setContent {
        ScreenNavigation()
    }

    @Test
    fun `first screen`(): Unit = with(compose) {
        onNodeWithText(ControlsTexts.PushSecondScreenButtonText).assertExists()
    }

    @Test
    fun `navigate to second screen`() = runTest {
        with(compose) {
            onNodeWithText(ControlsTexts.PushSecondScreenButtonText).assertExists()
            onNodeWithText(Arguments.SecondScreenArgs).assertDoesNotExist()

            onNodeWithText(ControlsTexts.PushSecondScreenButtonText).performClick()
            awaitIdle()

            onNodeWithText(ControlsTexts.PushSecondScreenButtonText).assertDoesNotExist()
            onNodeWithText(Arguments.SecondScreenArgs).assertExists()
        }
    }

    @Test
    fun `navigate to third screen`() = runTest {
        with(compose) {
            onNodeWithText(ControlsTexts.PushSecondScreenButtonText).performClick()
            awaitIdle()

            onNodeWithText(Arguments.SecondScreenArgs).assertExists()

            onNodeWithText(ControlsTexts.PushThirdScreenButtonText).performClick()
            awaitIdle()

            onNodeWithText(Arguments.SecondScreenArgs).assertDoesNotExist()
            onNodeWithText(Arguments.ThirdScreenArgs.toString()).assertExists()
        }
    }

    @Test
    fun `navigate to third screen then pop to second screen`() = runTest {
        with(compose) {
            onNodeWithText(ControlsTexts.PushSecondScreenButtonText).performClick()
            awaitIdle()

            onNodeWithText(ControlsTexts.PushThirdScreenButtonText).performClick()
            awaitIdle()

            onNodeWithText(Arguments.ThirdScreenArgs.toString()).assertExists()
            onNodeWithText(ControlsTexts.PopScreenButtonText).performClick()
            awaitIdle()

            onNodeWithText(Arguments.ThirdScreenArgs.toString()).assertDoesNotExist()
            onNodeWithText(Arguments.SecondScreenArgs).assertExists()
        }
    }

    @Test
    fun `navigate to third screen then pop to first screen`() = runTest {
        with(compose) {
            onNodeWithText(ControlsTexts.PushSecondScreenButtonText).performClick()
            awaitIdle()

            onNodeWithText(ControlsTexts.PushThirdScreenButtonText).performClick()
            awaitIdle()

            onNodeWithText(Arguments.ThirdScreenArgs.toString()).assertExists()
            onNodeWithText(ControlsTexts.PopToFirstScreenButtonText).performClick()
            awaitIdle()

            onNodeWithText(Arguments.ThirdScreenArgs.toString()).assertDoesNotExist()
            onNodeWithText(ControlsTexts.PushSecondScreenButtonText).assertExists()
        }
    }

    @Test
    fun `missing start route screen`() {
        shouldThrow<NoSuchElementException> {
            compose.setContent {
                ScreensNavigation(
                    startRoute = TestRoutes.FirstScreen
                ) {
                    screen(TestRoutes.SecondScreen) {}
                }
            }
        }
    }

    @Test
    fun `empty navigation graph`() {
        shouldThrow<NoSuchElementException> {
            compose.setContent {
                ScreensNavigation(
                    startRoute = TestRoutes.FirstScreen
                ) {}
            }
        }
    }
}
