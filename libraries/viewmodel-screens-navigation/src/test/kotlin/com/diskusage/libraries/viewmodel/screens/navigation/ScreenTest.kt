package com.diskusage.libraries.viewmodel.screens.navigation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.diskusage.libraries.viewmodel.screens.navigation.components.ScreenNavigation
import com.diskusage.libraries.viewmodel.screens.navigation.data.ControlsTexts
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ScreenTest {

    @get:Rule
    val compose = createComposeRule()

    @Before
    fun setUp() {
        compose.setContent {
            ScreenNavigation()
        }
    }

    @Test
    fun `first screen`(): Unit = with(compose) {
        onNode(hasSetTextAction()).assert(hasText(""))
        onNode(hasSetTextAction()).performTextInput("Foo")
        onNode(hasSetTextAction()).assert(hasText("Foo"))
    }

    @Test
    fun `navigate to second screen and update it's state`() = runTest {
        with(compose) {
            onNodeWithText(ControlsTexts.PushSecondScreenButtonText).performClick()
            awaitIdle()

            onNode(hasSetTextAction()).assert(hasText(""))
            onNode(hasSetTextAction()).performTextInput("Bar")
            onNode(hasSetTextAction()).assert(hasText("Bar"))
        }
    }

    @Test
    fun `navigate back and forth`() = runTest {
        with(compose) {
            onNode(hasSetTextAction()).assert(hasText(""))
            onNode(hasSetTextAction()).performTextInput("Foo")
            onNode(hasSetTextAction()).assert(hasText("Foo"))

            onNodeWithText(ControlsTexts.PushSecondScreenButtonText).performClick()
            awaitIdle()

            onNode(hasSetTextAction()).assert(hasText("Foo"))
            onNode(hasSetTextAction()).performTextClearance()
            onNode(hasSetTextAction()).performTextInput("The quick brown fox")
            onNode(hasSetTextAction()).assert(hasText("The quick brown fox"))

            onNodeWithText(ControlsTexts.PopScreenButtonText).performClick()
            awaitIdle()

            onNode(hasSetTextAction()).assert(hasText("Foo"))
            onNode(hasSetTextAction()).performTextInput("Bar")
            onNode(hasSetTextAction()).assert(hasText("FooBar"))

            onNodeWithText(ControlsTexts.PushSecondScreenButtonText).performClick()
            awaitIdle()

            onNode(hasSetTextAction()).assert(hasText("FooBar"))
        }
    }
}
