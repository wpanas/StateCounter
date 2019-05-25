package com.github.wpanas.statecounter.counter

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CounterFragmentProvider {
    @ContributesAndroidInjector
    abstract fun counterFragment(): CounterFragment
}