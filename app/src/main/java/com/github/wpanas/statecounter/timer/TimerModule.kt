package com.github.wpanas.statecounter.timer

import com.github.wpanas.statecounter.infra.utils.CountDownTimerBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TimerModule {
    @Provides
    @Singleton
    fun viewModel(): TimerViewModel {
        val countDownTimerBuilder = CountDownTimerBuilder()
        return TimerViewModel(countDownTimerBuilder)
    }
}