package com.diskusage.libraries.screens.navigation

import com.diskusage.libraries.screens.navigation.data.TestRoutes
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException

class ScreensMapBuilderTest {

    private lateinit var screensMapBuilder: ScreensMapBuilder

    @Before
    fun setUp() {
        screensMapBuilder = ScreensMapBuilder()
    }

    @Test
    fun `composables with unique routes`() {
        with(screensMapBuilder) {
            screen(route = TestRoutes.FirstScreen, content = {})
            screen(route = TestRoutes.SecondScreen, content = {})
            build().keys shouldContainExactlyInAnyOrder listOf(TestRoutes.FirstScreen, TestRoutes.SecondScreen)
        }
    }

    @Test
    fun `composables with not unique routes`() {
        with(screensMapBuilder) {
            screen(route = TestRoutes.FirstScreen, content = {})
            screen(route = TestRoutes.SecondScreen, content = {})
            shouldThrow<IllegalArgumentException> {
                screen(route = TestRoutes.FirstScreen, content = {})
            }
        }
    }
}
