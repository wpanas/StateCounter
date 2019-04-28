package com.github.wpanas.statecounter.counter

import android.view.View

interface CounterChanger {
    fun incrementState(view: View)

    fun decrementState(view: View)

    fun saveState(view: View)
}