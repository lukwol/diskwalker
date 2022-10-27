package com.diskusage.libraries.screens.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.diskusage.libraries.screens.navigation.data.TestRoutes
import io.kotest.assertions.throwables.shouldThrow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private object ButtonsTexts {
    const val PushFirstScreen = "Push First Screen"
    const val PushSecondScreen = "Push Second Screen"
    const val PopScreen = "Pop Screen"
    const val PopToStartScreen = "Pop To Start Screen"
}

private data class SecondScreenArgs(
    val text: String,
    val number: Int
)

private object Arguments {
    const val FirstScreen = "Foo"
    val SecondScreen = SecondScreenArgs(
        text = "Bar",
        number = 562
    )
}

@OptIn(ExperimentalCoroutinesApi::class)
class ScreensNavigationTest {
    @get:Rule
    val compose = createComposeRule()

    @Before
    fun setUp() = compose.setContent {
        ScreensNavigation(
            startRoute = TestRoutes.StartScreen
        ) {
            screen(TestRoutes.StartScreen) {
                val screensController = LocalScreensController.current

                Button(
                    onClick = { screensController.push(TestRoutes.FirstScreen, Arguments.FirstScreen) }
                ) {
                    Text(ButtonsTexts.PushFirstScreen)
                }
            }

            screen(TestRoutes.FirstScreen) { args ->
                val screensController = LocalScreensController.current

                Column {
                    Text(args as String)
                    Button(
                        onClick = { screensController.push(TestRoutes.SecondScreen, Arguments.SecondScreen) }
                    ) {
                        Text(ButtonsTexts.PushSecondScreen)
                    }
                }
            }

            screen(TestRoutes.SecondScreen) { args ->
                val screensController = LocalScreensController.current

                Column {
                    Text((args as SecondScreenArgs).toString())
                    Button(
                        onClick = { screensController.pop() }
                    ) {
                        Text(ButtonsTexts.PopScreen)
                    }
                    Button(
                        onClick = { screensController.pop(upToRoute = TestRoutes.StartScreen) }
                    ) {
                        Text(ButtonsTexts.PopToStartScreen)
                    }
                }
            }
        }
    }

    @Test
    fun `start screen`(): Unit = with(compose) {
        onNodeWithText(ButtonsTexts.PushFirstScreen).assertExists()
    }

    @Test
    fun `navigate to first screen`() = runTest {
        with(compose) {
            onNodeWithText(ButtonsTexts.PushFirstScreen).assertExists()
            onNodeWithText(Arguments.FirstScreen).assertDoesNotExist()

            onNodeWithText(ButtonsTexts.PushFirstScreen).performClick()
            awaitIdle()

            onNodeWithText(ButtonsTexts.PushFirstScreen).assertDoesNotExist()
            onNodeWithText(Arguments.FirstScreen).assertExists()
        }
    }

    @Test
    fun `navigate to second screen`() = runTest {
        with(compose) {
            onNodeWithText(ButtonsTexts.PushFirstScreen).performClick()
            awaitIdle()

            onNodeWithText(Arguments.FirstScreen).assertExists()

            onNodeWithText(ButtonsTexts.PushSecondScreen).performClick()
            awaitIdle()

            onNodeWithText(Arguments.FirstScreen).assertDoesNotExist()
            onNodeWithText(Arguments.SecondScreen.toString()).assertExists()
        }
    }

    @Test
    fun `navigate to second screen then pop to first screen`() = runTest {
        with(compose) {
            onNodeWithText(ButtonsTexts.PushFirstScreen).performClick()
            awaitIdle()

            onNodeWithText(ButtonsTexts.PushSecondScreen).performClick()
            awaitIdle()

            onNodeWithText(Arguments.SecondScreen.toString()).assertExists()
            onNodeWithText(ButtonsTexts.PopScreen).performClick()
            awaitIdle()

            onNodeWithText(Arguments.SecondScreen.toString()).assertDoesNotExist()
            onNodeWithText(Arguments.FirstScreen).assertExists()
        }
    }

    @Test
    fun `navigate to second screen then pop to start screen`() = runTest {
        with(compose) {
            onNodeWithText(ButtonsTexts.PushFirstScreen).performClick()
            awaitIdle()

            onNodeWithText(ButtonsTexts.PushSecondScreen).performClick()
            awaitIdle()

            onNodeWithText(Arguments.SecondScreen.toString()).assertExists()
            onNodeWithText(ButtonsTexts.PopToStartScreen).performClick()
            awaitIdle()

            onNodeWithText(Arguments.SecondScreen.toString()).assertDoesNotExist()
            onNodeWithText(ButtonsTexts.PushFirstScreen).assertExists()
        }
    }

    @Test
    fun `missing start route composable`() {
        shouldThrow<NoSuchElementException> {
            compose.setContent {
                ScreensNavigation(
                    startRoute = TestRoutes.StartScreen
                ) {
                    screen(TestRoutes.FirstScreen) {}
                }
            }
        }
    }

    @Test
    fun `empty navigation graph`() {
        shouldThrow<NoSuchElementException> {
            compose.setContent {
                ScreensNavigation(
                    startRoute = TestRoutes.StartScreen
                ) {}
            }
        }
    }
}
