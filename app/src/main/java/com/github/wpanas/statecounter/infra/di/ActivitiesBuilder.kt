package com.github.wpanas.statecounter.infra.di

import com.github.wpanas.statecounter.MainActivity
import com.github.wpanas.statecounter.counter.CounterFragmentProvider
import com.github.wpanas.statecounter.main.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuilder {
    @ContributesAndroidInjector(modules = [MainActivityModule::class, CounterFragmentProvider::class])
    abstract fun bindMainActivity(): MainActivity
}