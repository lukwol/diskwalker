package com.diskusage.libraries.viewmodel.screens.navigation

import androidx.compose.material.Text
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ScreenTest {

    @get:Rule
    val compose = createComposeRule()

    @Before
    fun setUp() {
        compose.setContent {
            Text("Example")
        }
    }

    @Test
    fun example() {
        compose.onNodeWithText("Example").assertExists()
    }
}
