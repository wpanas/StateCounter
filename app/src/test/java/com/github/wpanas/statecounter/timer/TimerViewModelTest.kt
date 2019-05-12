package com.github.wpanas.statecounter.timer

import android.os.CountDownTimer
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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

    @Before
    fun setup() {
        countDownTimerBuilder = mockk(relaxed = true)
        countDownTimer = mockk(relaxed = true)

        every {
            countDownTimerBuilder.build()
        } returns countDownTimer
    }

    @Test
    fun `should have default zero state`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder)

        // then
        assertEquals(0, viewModel.counter.value)
    }

    @Test
    fun `should increment counter state`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder)

        // when
        viewModel.increment()

        // then
        assertEquals(1, viewModel.counter.value)
    }

    @Test
    fun `should decrement counter state not less than 0`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder)

        viewModel.increment()
        viewModel.increment()

        // when
        viewModel.decrement()
        viewModel.decrement()
        viewModel.decrement()

        // then
        assertEquals(0, viewModel.counter.value)
    }

    @Test
    fun `should start counting from state`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder)

        viewModel.increment()
        viewModel.increment()

        // when
        viewModel.start()

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
        val viewModel = TimerViewModel(countDownTimerBuilder)
        val decrement = slot<(millisUntilFinished: Long) -> Unit>()

        every {
            countDownTimerBuilder.onTick(capture(decrement))
        } returns countDownTimerBuilder

        viewModel.start()
        viewModel.increment()
        viewModel.increment()

        every {
            countDownTimer.onTick(1000)
        } returns decrement.captured(1000)

        countDownTimer.onTick(1000)

        // when
        viewModel.pause()

        // then
        assertEquals(viewModel.counter.value, 1)

        verify {
            countDownTimer.start()
            countDownTimer.onTick(1000)
            countDownTimer.cancel()
        }

        confirmVerified(countDownTimer)
    }

    @Test
    fun `should reset counting after started`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder)
        val decrement = slot<(millisUntilFinished: Long) -> Unit>()

        every {
            countDownTimerBuilder.onTick(capture(decrement))
        } returns countDownTimerBuilder

        viewModel.start()
        viewModel.increment()
        viewModel.increment()

        every {
            countDownTimer.onTick(1000)
        } returns decrement.captured(1000)

        countDownTimer.onTick(1000)

        // when
        viewModel.stop()

        // then
        assertEquals(viewModel.counter.value, 2)

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
        val viewModel = TimerViewModel(countDownTimerBuilder)

        viewModel.start()

        // when
        viewModel.start()

        // then
        verify(exactly = 1) {
            countDownTimerBuilder.unit(TimeUnit.SECONDS)
            countDownTimerBuilder.timeInFuture(0)
            countDownTimerBuilder.countDownInterval(1)
            countDownTimerBuilder.onTick(any())
            countDownTimerBuilder.onFinish(any())
            countDownTimerBuilder.build()
        }

        confirmVerified(countDownTimerBuilder)
    }

    @Test
    fun `should start countdown after other is stopped`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder)

        viewModel.start()
        viewModel.stop()

        // when
        viewModel.start()

        // then
        verify(exactly = 2) {
            countDownTimerBuilder.unit(TimeUnit.SECONDS)
            countDownTimerBuilder.timeInFuture(0)
            countDownTimerBuilder.countDownInterval(1)
            countDownTimerBuilder.onTick(any())
            countDownTimerBuilder.onFinish(any())
            countDownTimerBuilder.build()
        }

        confirmVerified(countDownTimerBuilder)
    }

    @Test
    fun `should start countdown after other is finished`() {
        // given
        val viewModel = TimerViewModel(countDownTimerBuilder)
        val finished = slot<() -> Unit>()

        every { countDownTimerBuilder.onFinish(capture(finished)) } returns countDownTimerBuilder

        viewModel.start()

        every { countDownTimer.onFinish() } returns finished.captured()

        countDownTimer.onFinish()

        // when
        viewModel.start()

        // then
        verify(exactly = 2) {
            countDownTimerBuilder.unit(TimeUnit.SECONDS)
            countDownTimerBuilder.timeInFuture(0)
            countDownTimerBuilder.countDownInterval(1)
            countDownTimerBuilder.onTick(any())
            countDownTimerBuilder.onFinish(any())
            countDownTimerBuilder.build()
        }

        confirmVerified(countDownTimerBuilder)
    }
}