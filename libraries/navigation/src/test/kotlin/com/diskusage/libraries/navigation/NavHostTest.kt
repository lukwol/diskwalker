package com.diskusage.libraries.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.diskusage.libraries.navigation.screens.LocalNavController
import com.diskusage.libraries.navigation.screens.NavHost
import com.diskusage.libraries.navigation.stubs.Routes
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
class NavHostTest {
    @get:Rule
    val compose = createComposeRule()

    @Before
    fun setUp() = compose.setContent {
        NavHost(
            startRoute = Routes.start
        ) {
            composable(Routes.start) {
                val navController = LocalNavController.current

                Button(
                    onClick = { navController.push(Routes.first, Arguments.FirstScreen) }
                ) {
                    Text(ButtonsTexts.PushFirstScreen)
                }
            }

            composable(Routes.first) { args ->
                val navController = LocalNavController.current

                Column {
                    Text(args as String)
                    Button(
                        onClick = { navController.push(Routes.second, Arguments.SecondScreen) }
                    ) {
                        Text(ButtonsTexts.PushSecondScreen)
                    }
                }
            }

            composable(Routes.second) { args ->
                val navController = LocalNavController.current

                Column {
                    Text((args as SecondScreenArgs).toString())
                    Button(
                        onClick = { navController.pop() }
                    ) {
                        Text(ButtonsTexts.PopScreen)
                    }
                    Button(
                        onClick = { navController.pop(upToRoute = Routes.start) }
                    ) {
                        Text(ButtonsTexts.PopToStartScreen)
                    }
                }
            }
        }
    }

    @Test
    fun `start screen`() {
        compose.onNodeWithText(ButtonsTexts.PushFirstScreen).assertExists()
    }

    @Test
    fun `navigate to first screen`() = runTest {
        compose.onNodeWithText(ButtonsTexts.PushFirstScreen).assertExists()
        compose.onNodeWithText(Arguments.FirstScreen).assertDoesNotExist()

        compose.onNodeWithText(ButtonsTexts.PushFirstScreen).performClick()
        compose.awaitIdle()

        compose.onNodeWithText(ButtonsTexts.PushFirstScreen).assertDoesNotExist()
        compose.onNodeWithText(Arguments.FirstScreen).assertExists()
    }

    @Test
    fun `navigate to second screen`() = runTest {
        compose.onNodeWithText(ButtonsTexts.PushFirstScreen).performClick()
        compose.awaitIdle()

        compose.onNodeWithText(Arguments.FirstScreen).assertExists()

        compose.onNodeWithText(ButtonsTexts.PushSecondScreen).performClick()
        compose.awaitIdle()

        compose.onNodeWithText(Arguments.FirstScreen).assertDoesNotExist()
        compose.onNodeWithText(Arguments.SecondScreen.toString()).assertExists()
    }

    @Test
    fun `navigate to second screen then pop to first screen`() = runTest {
        compose.onNodeWithText(ButtonsTexts.PushFirstScreen).performClick()
        compose.awaitIdle()

        compose.onNodeWithText(ButtonsTexts.PushSecondScreen).performClick()
        compose.awaitIdle()

        compose.onNodeWithText(Arguments.SecondScreen.toString()).assertExists()
        compose.onNodeWithText(ButtonsTexts.PopScreen).performClick()
        compose.awaitIdle()

        compose.onNodeWithText(Arguments.SecondScreen.toString()).assertDoesNotExist()
        compose.onNodeWithText(Arguments.FirstScreen).assertExists()
    }

    @Test
    fun `navigate to second screen then pop to start screen`() = runTest {
        compose.onNodeWithText(ButtonsTexts.PushFirstScreen).performClick()
        compose.awaitIdle()

        compose.onNodeWithText(ButtonsTexts.PushSecondScreen).performClick()
        compose.awaitIdle()

        compose.onNodeWithText(Arguments.SecondScreen.toString()).assertExists()
        compose.onNodeWithText(ButtonsTexts.PopToStartScreen).performClick()
        compose.awaitIdle()

        compose.onNodeWithText(Arguments.SecondScreen.toString()).assertDoesNotExist()
        compose.onNodeWithText(ButtonsTexts.PushFirstScreen).assertExists()
    }

    @Test
    fun `missing start route composable`() {
        shouldThrow<NoSuchElementException> {
            compose.setContent {
                NavHost(
                    startRoute = Routes.start
                ) {
                    composable(Routes.first) {}
                }
            }
        }
    }

    @Test
    fun `empty navigation graph`() {
        shouldThrow<NoSuchElementException> {
            compose.setContent {
                NavHost(
                    startRoute = Routes.start
                ) {}
            }
        }
    }
}
