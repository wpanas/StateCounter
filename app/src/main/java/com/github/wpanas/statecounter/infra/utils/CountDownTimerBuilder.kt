package com.github.wpanas.statecounter.infra.utils

import android.os.CountDownTimer
import java.util.concurrent.TimeUnit

class CountDownTimerBuilder {
    private var countDownInterval: Long = 0
    private var millisInFuture: Long = 0
    private var onTickHandler: (millisUntilFinished: Long) -> Unit = fun(_: Long) {}
    private var onFinishHandler: () -> Unit = fun() {}
    private var unit: TimeUnit = TimeUnit.MILLISECONDS

    fun timeInFuture(timeInFuture: Long): CountDownTimerBuilder {
        this.millisInFuture = timeInFuture
        return this
    }

    fun countDownInterval(countDownInterval: Long): CountDownTimerBuilder {
        this.countDownInterval = countDownInterval
        return this
    }

    fun onTick(handler: (millisUntilFinished: Long) -> Unit): CountDownTimerBuilder {
        this.onTickHandler = handler
        return this
    }

    fun onFinish(handler: () -> Unit): CountDownTimerBuilder {
        this.onFinishHandler = handler
        return this
    }

    fun unit(unit: TimeUnit): CountDownTimerBuilder {
        this.unit = unit
        return this
    }

    fun build(): CountDownTimer =
        object : CountDownTimer(unit.toMillis(millisInFuture), unit.toMillis(countDownInterval)) {
            override fun onFinish() = onFinishHandler()

            override fun onTick(millisUntilFinished: Long) = onTickHandler(millisUntilFinished)
        }
}