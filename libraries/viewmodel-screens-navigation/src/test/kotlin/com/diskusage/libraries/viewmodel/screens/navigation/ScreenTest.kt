package com.diskusage.libraries.viewmodel.screens.navigation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.diskusage.libraries.viewmodel.screens.navigation.components.ScreenNavigation
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
        onNode(hasSetTextAction()).performTextInput("FooBar")
        onNode(hasSetTextAction()).assert(hasText("FooBar"))
    }
}
