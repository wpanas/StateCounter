package com.github.wpanas.statecounter.timer

import android.os.Bundle
import com.github.wpanas.statecounter.R
import dagger.android.support.DaggerAppCompatActivity

class TimerActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
    }
}
