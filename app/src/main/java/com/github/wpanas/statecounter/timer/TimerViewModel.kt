package com.github.wpanas.statecounter.timer

import android.os.CountDownTimer
import com.github.wpanas.statecounter.counter.Counter
import com.github.wpanas.statecounter.counter.CounterViewModel
import com.github.wpanas.statecounter.infra.utils.CountDownTimerBuilder
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

class TimerViewModel(
    private val countDownTimerBuilder: CountDownTimerBuilder,
    private val counterViewModel: CounterViewModel
) : Counter {
    private val internalCounter = AtomicInteger()
    private val countDownTimer = AtomicReference<CountDownTimer>()

    var counter: Int = 0
        private set
        get() = internalCounter.get()

    override fun increment() {
        counterViewModel.increment()
        internalCounter.set(counterViewModel.counter.value ?: 0)
    }

    override fun decrement() {
        if (counterViewModel.counter.value!! > 0) {
            counterViewModel.decrement()
            internalCounter.set(counterViewModel.counter.value ?: 0)
        }
    }

    override fun reset(state: Int) {
        pause()
        counterViewModel.reset()
        internalCounter.set(state)
        countDownTimer.set(null)
    }

    fun pause() {
        if (countDownTimer.get() != null) {
            countDownTimer.get().cancel()
        }
    }

    fun start(onFinish: () -> Unit) {
        val oldTimer = countDownTimer.get()
        if (oldTimer == null) {
            val timer = countDownTimerBuilder.apply {
                unit(TimeUnit.SECONDS)
                countDownInterval(1)
                timeInFuture(internalCounter.toLong())
                onTick { counterViewModel.decrement() }
                onFinish {
                    countDownTimer.set(null)
                    onFinish()
                }
            }.build()

            if (countDownTimer.compareAndSet(null, timer)) {
                timer.start()
            }
        }
    }
}