package com.diskusage.libraries.screens.navigation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.diskusage.libraries.screens.navigation.components.ScreenNavigation
import com.diskusage.libraries.screens.navigation.data.Arguments
import com.diskusage.libraries.screens.navigation.data.ButtonsTexts
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
    fun `start screen`(): Unit = with(compose) {
        onNodeWithText(ButtonsTexts.PushSecondScreen).assertExists()
    }

    @Test
    fun `navigate to first screen`() = runTest {
        with(compose) {
            onNodeWithText(ButtonsTexts.PushSecondScreen).assertExists()
            onNodeWithText(Arguments.SecondScreenArgs).assertDoesNotExist()

            onNodeWithText(ButtonsTexts.PushSecondScreen).performClick()
            awaitIdle()

            onNodeWithText(ButtonsTexts.PushSecondScreen).assertDoesNotExist()
            onNodeWithText(Arguments.SecondScreenArgs).assertExists()
        }
    }

    @Test
    fun `navigate to second screen`() = runTest {
        with(compose) {
            onNodeWithText(ButtonsTexts.PushSecondScreen).performClick()
            awaitIdle()

            onNodeWithText(Arguments.SecondScreenArgs).assertExists()

            onNodeWithText(ButtonsTexts.PushThirdScreen).performClick()
            awaitIdle()

            onNodeWithText(Arguments.SecondScreenArgs).assertDoesNotExist()
            onNodeWithText(Arguments.ThirdScreenArgs.toString()).assertExists()
        }
    }

    @Test
    fun `navigate to second screen then pop to first screen`() = runTest {
        with(compose) {
            onNodeWithText(ButtonsTexts.PushSecondScreen).performClick()
            awaitIdle()

            onNodeWithText(ButtonsTexts.PushThirdScreen).performClick()
            awaitIdle()

            onNodeWithText(Arguments.ThirdScreenArgs.toString()).assertExists()
            onNodeWithText(ButtonsTexts.PopScreen).performClick()
            awaitIdle()

            onNodeWithText(Arguments.ThirdScreenArgs.toString()).assertDoesNotExist()
            onNodeWithText(Arguments.SecondScreenArgs).assertExists()
        }
    }

    @Test
    fun `navigate to second screen then pop to start screen`() = runTest {
        with(compose) {
            onNodeWithText(ButtonsTexts.PushSecondScreen).performClick()
            awaitIdle()

            onNodeWithText(ButtonsTexts.PushThirdScreen).performClick()
            awaitIdle()

            onNodeWithText(Arguments.ThirdScreenArgs.toString()).assertExists()
            onNodeWithText(ButtonsTexts.PopToFirstScreen).performClick()
            awaitIdle()

            onNodeWithText(Arguments.ThirdScreenArgs.toString()).assertDoesNotExist()
            onNodeWithText(ButtonsTexts.PushSecondScreen).assertExists()
        }
    }

    @Test
    fun `missing start route composable`() {
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
