package com.github.wpanas.statecounter.counter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class CounterViewModel : ViewModel() {
    protected val liveData: MutableLiveData<Int> by lazy {
        val mutableLiveData = MutableLiveData<Int>()
        mutableLiveData.value = 0
        return@lazy mutableLiveData
    }

    open fun increment() {
        liveData.value = liveData.value?.plus(1)
    }

    open fun decrement() {
        liveData.value = liveData.value?.minus(1)
    }

    val counter: LiveData<Int> = liveData
}