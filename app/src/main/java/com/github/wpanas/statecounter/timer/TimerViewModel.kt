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
        countDownTimer.set(null)
    }

    fun pause() {
        if (countDownTimer.get() != null) {
            countDownTimer.get().cancel()
        }
    }

    fun start() {
        val oldTimer = countDownTimer.get()
        if (oldTimer == null) {
            val timer = countDownTimerBuilder.apply {
                unit(TimeUnit.SECONDS)
                countDownInterval(1)
                timeInFuture(internalCounter.toLong())
                onTick { super.decrement() }
                onFinish { countDownTimer.set(null) }
            }.build()

            countDownTimer.compareAndSet(null, timer)

            timer.start()
        }
    }
}