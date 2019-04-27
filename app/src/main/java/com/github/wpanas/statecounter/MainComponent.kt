package com.github.wpanas.statecounter

import android.app.Application
import com.github.wpanas.statecounter.action.ActionModule
import com.github.wpanas.statecounter.counter.CounterModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [ActionModule::class, CounterModule::class])
interface MainComponent: AndroidInjector<MainActivity> {
    fun inject(application: Application)
}