package com.github.wpanas.statecounter.counter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CounterViewModel: ViewModel() {
    private val liveData: MutableLiveData<Int> by lazy {
        val mutableLiveData = MutableLiveData<Int>()
        mutableLiveData.value = 0
        return@lazy mutableLiveData
    }

    fun increment() {
        liveData.value = liveData.value?.plus(1)
    }

    fun decrement() {
        liveData.value = liveData.value?.minus(1)
    }

    val counter: LiveData<Int> = liveData
}