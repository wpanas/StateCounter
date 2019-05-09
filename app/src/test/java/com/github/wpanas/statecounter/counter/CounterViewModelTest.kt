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
        // given
        val viewModel = CounterViewModel()

        // then
        assertEquals(0, viewModel.counter.value)
    }

    @Test
    fun `should increment counter state`() {
        // given
        val viewModel = CounterViewModel()

        // when
        viewModel.increment()

        // then
        assertEquals(1, viewModel.counter.value)
    }

    @Test
    fun `should decrement counter state`() {
        // given
        val viewModel = CounterViewModel()

        // when
        viewModel.decrement()

        // then
        assertEquals(-1, viewModel.counter.value)
    }
}