package com.github.wpanas.statecounter

import com.github.wpanas.statecounter.action.ActionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class StateCounterApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder()
            .applicationBind(this)
            .actionModule(ActionModule(this))
            .build()
        appComponent.inject(this)

        return appComponent
    }
}