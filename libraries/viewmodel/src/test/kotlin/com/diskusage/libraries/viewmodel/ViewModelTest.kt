package com.diskusage.libraries.viewmodel

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelTest {

    private val viewModel = object : ViewModel() {}

    @Test
    fun cancelled() = runTest {
        val result = viewModel.viewModelScope.async {
            delay(10L)
            42
        }
        viewModel.viewModelScope.cancel()
        shouldThrow<CancellationException> {
            result.await()
        }
    }

    @Test
    fun `not cancelled`() = runTest {
        val result = viewModel.viewModelScope.async {
            delay(10L)
            42
        }
        result.await() shouldBe 42
    }
}
