package com.github.wpanas.statecounter.timer

import android.os.CountDownTimer
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.wpanas.statecounter.counter.CounterViewModel
import com.github.wpanas.statecounter.infra.utils.CountDownTimerBuilder
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

class TimerViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var countDownTimerBuilder: CountDownTimerBuilder
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var counterViewModel: CounterViewModel

    @Before
    fun setup() {
        countDownTimerBuilder = mockk(relaxed = true)
        countDownTimer = mockk(relaxed = true)
        counterViewModel = spyk()

        every {
            countDownTimerBuilder.build()
        } returns countDownTimer
    }

    @Test
    fun `should have default zero state`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder, counterViewModel)

        // then
        assertEquals(0, viewModel.counter)
    }

    @Test
    fun `should increment counter state`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder, counterViewModel)

        // when
        viewModel.increment()

        // then
        verify {
            counterViewModel.increment()
            counterViewModel.counter
        }

        assertEquals(1, viewModel.counter)

        confirmVerified(counterViewModel)
    }

    @Test
    fun `should decrement counter state not less than 0`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder, counterViewModel)

        viewModel.increment()
        viewModel.increment()

        // when
        viewModel.decrement()
        viewModel.decrement()
        viewModel.decrement()

        // then
        verify(exactly = 2) {
            counterViewModel.increment()
            counterViewModel.decrement()
        }

        assertEquals(0, viewModel.counter)
    }

    @Test
    fun `should start counting from state`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder, counterViewModel)

        viewModel.increment()
        viewModel.increment()

        // when
        viewModel.start {}

        // then
        verify {
            countDownTimerBuilder.unit(TimeUnit.SECONDS)
            countDownTimerBuilder.timeInFuture(2)
            countDownTimerBuilder.countDownInterval(1)
            countDownTimerBuilder.onTick(any())
            countDownTimerBuilder.onFinish(any())
            countDownTimerBuilder.build()

            countDownTimer.start()
        }

        confirmVerified(countDownTimer, countDownTimerBuilder)
    }

    @Test
    fun `should pause counting after counting started`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder, counterViewModel)
        val decrement = slot<(millisUntilFinished: Long) -> Unit>()

        every {
            countDownTimerBuilder.onTick(capture(decrement))
        } returns countDownTimerBuilder

        viewModel.start {}
        viewModel.increment()
        viewModel.increment()

        every {
            countDownTimer.onTick(1000)
        } returns decrement.captured(1000)

        countDownTimer.onTick(1000)

        // when
        viewModel.pause()

        // then
        assertEquals(2, viewModel.counter)
        assertEquals(1, counterViewModel.counter.value)

        verify {
            counterViewModel.increment()
            counterViewModel.counter
            counterViewModel.increment()
            counterViewModel.counter

            counterViewModel.decrement()
            counterViewModel.counter

            countDownTimer.start()
            countDownTimer.onTick(1000)
            countDownTimer.cancel()
        }

        confirmVerified(counterViewModel, countDownTimer)
    }

    @Test
    fun `should reset counting after started`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder, counterViewModel)
        val decrement = slot<(millisUntilFinished: Long) -> Unit>()

        every {
            countDownTimerBuilder.onTick(capture(decrement))
        } returns countDownTimerBuilder

        viewModel.start {}
        viewModel.increment()
        viewModel.increment()

        every {
            countDownTimer.onTick(1000)
        } returns decrement.captured(1000)

        countDownTimer.onTick(1000)

        // when
        viewModel.reset()

        // then
        assertEquals(0, viewModel.counter)

        verify {
            countDownTimer.start()
            countDownTimer.onTick(1000)
            countDownTimer.cancel()
        }

        confirmVerified(countDownTimer)
    }

    @Test
    fun `should not start countdown before other is finished`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder, counterViewModel)

        viewModel.start {}

        // when
        viewModel.start {}

        // then
        verify(exactly = 1) {
            countDownTimerBuilder.unit(TimeUnit.SECONDS)
            countDownTimerBuilder.timeInFuture(0)
            countDownTimerBuilder.countDownInterval(1)
            countDownTimerBuilder.onTick(any())
            countDownTimerBuilder.onFinish(any())
            countDownTimerBuilder.build()
            countDownTimer.start()
        }

        confirmVerified(countDownTimerBuilder)
    }

    @Test
    fun `should start countdown after other is stopped`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder, counterViewModel)

        viewModel.start {}
        viewModel.reset()

        // when
        viewModel.start {}

        // then
        verify(exactly = 2) {
            countDownTimerBuilder.unit(TimeUnit.SECONDS)
            countDownTimerBuilder.timeInFuture(0)
            countDownTimerBuilder.countDownInterval(1)
            countDownTimerBuilder.onTick(any())
            countDownTimerBuilder.onFinish(any())
            countDownTimerBuilder.build()
            countDownTimer.start()
        }

        confirmVerified(countDownTimerBuilder)
    }

    @Test
    fun `should start countdown after other is finished`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder, counterViewModel)
        val finished = slot<() -> Unit>()

        every { countDownTimerBuilder.onFinish(capture(finished)) } returns countDownTimerBuilder

        viewModel.start {}

        every { countDownTimer.onFinish() } returns finished.captured()

        countDownTimer.onFinish()

        // when
        viewModel.start {}

        // then
        verify(exactly = 2) {
            countDownTimerBuilder.unit(TimeUnit.SECONDS)
            countDownTimerBuilder.timeInFuture(0)
            countDownTimerBuilder.countDownInterval(1)
            countDownTimerBuilder.onTick(any())
            countDownTimerBuilder.onFinish(any())
            countDownTimerBuilder.build()
            countDownTimer.start()
        }

        confirmVerified(countDownTimerBuilder)
    }

    @Test
    fun `should call onFinish after countdown is finished`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder, counterViewModel)
        val finished = slot<() -> Unit>()
        val onFinish = spyk<() -> Unit>()

        every { countDownTimerBuilder.onFinish(capture(finished)) } returns countDownTimerBuilder

        viewModel.start {
            onFinish()
        }

        every { countDownTimer.onFinish() } returns finished.captured()

        // when
        countDownTimer.onFinish()

        // then
        verify(exactly = 1) {
            onFinish()
        }
    }
}