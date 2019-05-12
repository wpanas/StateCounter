package com.github.wpanas.statecounter.infra.di

import com.github.wpanas.statecounter.MainActivity
import com.github.wpanas.statecounter.counter.CounterActivity
import com.github.wpanas.statecounter.counter.CounterFragmentProvider
import com.github.wpanas.statecounter.timer.TimerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [CounterFragmentProvider::class])
    abstract fun bindCounterActivity(): CounterActivity

    @ContributesAndroidInjector
    abstract fun bindTimerActivity(): TimerActivity
}