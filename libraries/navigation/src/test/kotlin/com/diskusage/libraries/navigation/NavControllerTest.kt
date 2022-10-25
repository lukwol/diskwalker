package com.diskusage.libraries.navigation

import com.diskusage.libraries.navigation.screens.NavController
import com.diskusage.libraries.navigation.screens.NavControllerImpl
import com.diskusage.libraries.navigation.stubs.Routes
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.Before
import org.junit.Test

class NavControllerTest {

    private lateinit var navController: NavController

    @Before
    fun setUp() {
        navController = NavControllerImpl(Routes.start)
    }

    @Test
    fun `initial routes`() {
        navController.routes shouldBe listOf(Routes.start)
    }

    @Test
    fun `pushing same route multiple times then popping up to it`() {
        navController.push(Routes.first)
        navController.push(Routes.second)
        navController.push(Routes.third)
        navController.push(Routes.first)
        navController.push(Routes.fourth)
        navController.routes shouldBe listOf(
            Routes.start,
            Routes.first,
            Routes.second,
            Routes.third,
            Routes.first,
            Routes.fourth
        )
        navController.pop(Routes.first)
        navController.routes shouldBe listOf(
            Routes.start,
            Routes.first,
            Routes.second,
            Routes.third,
            Routes.first
        )
        navController.pop()
        navController.pop(Routes.first)
        navController.routes shouldBe listOf(
            Routes.start,
            Routes.first
        )
        navController.pop()
        navController.routes shouldBe listOf(
            Routes.start
        )
    }

    @Test
    fun `popping start route`() {
        shouldThrow<IllegalStateException> {
            navController.pop()
        }
    }

    @Test
    fun `popping route that is not on the stack`() {
        shouldThrow<IllegalArgumentException> {
            navController.pop(Routes.first)
        }
    }

    @Test
    fun `popping to current route`() {
        navController.push(Routes.first)
        shouldThrow<IllegalArgumentException> {
            navController.pop(Routes.first)
        }
    }
}
