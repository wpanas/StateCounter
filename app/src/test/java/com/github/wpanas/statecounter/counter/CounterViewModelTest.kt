package com.github.wpanas.statecounter.counter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CounterViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Test
    fun `should have default zero state`() {
        val viewModel = CounterViewModel()

        assertEquals(0, viewModel.data.value)
    }

    @Test
    fun `should increment state`() {
        val viewModel = CounterViewModel()

        viewModel.increment()

        assertEquals(1, viewModel.data.value)
    }

    @Test
    fun `should decrement state`() {
        val viewModel = CounterViewModel()

        viewModel.decrement()

        assertEquals(-1, viewModel.data.value)
    }
}