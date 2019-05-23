package com.github.wpanas.statecounter.timer

import android.os.Bundle
import android.view.View
import com.github.wpanas.statecounter.R
import com.github.wpanas.statecounter.counter.CounterChanger
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class TimerActivity : DaggerAppCompatActivity(), CounterChanger {

    @Inject
    lateinit var timerViewModel: TimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        timerViewModel.reset()
    }

    @Suppress("UNUSED_PARAMETER")
    fun start(view: View) {
        timerViewModel.start()
    }

    override fun incrementState(view: View) {
        timerViewModel.increment()
    }

    override fun decrementState(view: View) {
        timerViewModel.decrement()
    }
}
