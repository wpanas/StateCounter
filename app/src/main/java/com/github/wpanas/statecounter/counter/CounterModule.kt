package com.github.wpanas.statecounter.counter

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CounterModule {

    @Provides
    @Singleton
    fun viewModel(): CounterViewModel {
        return ViewModelProvider.NewInstanceFactory().create(CounterViewModel::class.java)
    }
}