package com.diskusage.libraries.navigation

import com.diskusage.libraries.navigation.stubs.Routes
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException

class NavMapBuilderTest {

    private lateinit var navMapBuilder: NavMapBuilder

    @Before
    fun setUp() {
        navMapBuilder = NavMapBuilder()
    }

    @Test
    fun `composables with unique routes`() {
        with(navMapBuilder) {
            composable(route = Routes.first, content = {})
            composable(route = Routes.second, content = {})
            build().keys shouldContainExactlyInAnyOrder listOf(Routes.first, Routes.second)
        }
    }

    @Test
    fun `composables with not unique routes`() {
        with(navMapBuilder) {
            composable(route = Routes.first, content = {})
            composable(route = Routes.second, content = {})
            shouldThrow<IllegalArgumentException> {
                composable(route = Routes.first, content = {})
            }
        }
    }
}
