package com.github.wpanas.statecounter

import com.github.wpanas.statecounter.action.ActionModule
import com.github.wpanas.statecounter.counter.CounterModule
import com.github.wpanas.statecounter.infra.di.ActivitiesBuilder
import com.github.wpanas.statecounter.timer.TimerModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActionModule::class, CounterModule::class, TimerModule::class, ActivitiesBuilder::class])
interface AppComponent : AndroidInjector<DaggerApplication> {
    fun inject(application: StateCounterApplication)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun applicationBind(application: StateCounterApplication): Builder

        fun actionModule(actionModule: ActionModule): Builder
    }
}