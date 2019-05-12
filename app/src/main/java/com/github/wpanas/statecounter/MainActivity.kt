package com.github.wpanas.statecounter

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.github.wpanas.statecounter.counter.CounterActivity
import com.github.wpanas.statecounter.timer.TimerActivity
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Suppress("UNUSED_PARAMETER")
    fun goToCounter(view: View) {
        val intent = Intent(this, CounterActivity::class.java)
        startActivity(intent)
    }

    @Suppress("UNUSED_PARAMETER")
    fun goToTimer(view: View) {
        val intent = Intent(this, TimerActivity::class.java)
        startActivity(intent)
    }
}
