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
    fun `canceling view model scope`() = runTest {
        val result1 = viewModel.viewModelScope.async {
            delay(10L)
            42
        }
        val result2 = viewModel.viewModelScope.async {
            delay(10L)
            "foo"
        }

        viewModel.viewModelScope.cancel()

        shouldThrow<CancellationException> {
            result1.await()
            result2.await()
        }
    }

    @Test
    fun `failing one of the child coroutines`() = runTest {
        val result1 = viewModel.viewModelScope.async {
            delay(10L)
            42
        }
        val result2 = viewModel.viewModelScope.async {
            throw RuntimeException()
        }

        result1.await() shouldBe 42
        shouldThrow<RuntimeException> {
            result2.await()
        }
    }

    @Test
    fun `completing all coroutines`() = runTest {
        val result1 = viewModel.viewModelScope.async {
            delay(10L)
            42
        }
        val result2 = viewModel.viewModelScope.async {
            delay(10L)
            "foo"
        }
        result1.await() shouldBe 42
        result2.await() shouldBe "foo"
    }
}
