package com.github.wpanas.statecounter.counter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class CounterViewModel : ViewModel(), Counter {
    private val liveData: MutableLiveData<Int> by lazy {
        val mutableLiveData = MutableLiveData<Int>()
        mutableLiveData.value = 0
        return@lazy mutableLiveData
    }

    override fun increment() {
        liveData.value = liveData.value?.plus(1)
    }

    override fun decrement() {
        liveData.value = liveData.value?.minus(1)
    }

    override fun reset(state: Int) {
        liveData.value = state
    }

    val counter: LiveData<Int> = liveData
}

interface Counter {
    fun increment()
    fun decrement()
    fun reset(state: Int = 0)
}