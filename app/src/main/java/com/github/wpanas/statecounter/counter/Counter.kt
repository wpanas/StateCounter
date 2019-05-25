package com.github.wpanas.statecounter.counter

interface Counter {
    fun increment()
    fun decrement()
    fun reset(state: Int = 0)
}