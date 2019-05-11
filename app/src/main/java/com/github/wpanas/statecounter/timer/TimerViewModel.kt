package com.github.wpanas.statecounter.timer

import android.os.CountDownTimer
import com.github.wpanas.statecounter.counter.CounterViewModel
import com.github.wpanas.statecounter.infra.utils.CountDownTimerBuilder
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

class TimerViewModel(private val countDownTimerBuilder: CountDownTimerBuilder) : CounterViewModel() {
    private val internalCounter = AtomicInteger()
    private val countDownTimer = AtomicReference<CountDownTimer>()

    override fun increment() {
        super.increment()
        internalCounter.incrementAndGet()
    }

    override fun decrement() {
        if (counter.value!! > 0) {
            super.decrement()
            internalCounter.decrementAndGet()
        }
    }

    fun stop() {
        pause()
        liveData.value = internalCounter.get()
    }

    fun pause() {
        if (countDownTimer.get() != null) {
            countDownTimer.get().cancel()
        }
    }

    fun start() {
        if (countDownTimer.get() == null) {
            val timer = countDownTimerBuilder.apply {
                unit(TimeUnit.SECONDS)
                countDownInterval(1)
                timeInFuture(internalCounter.toLong())
                onTick { super.decrement() }
            }.build()

            countDownTimer.set(timer)

            timer.start()
        }
    }
}