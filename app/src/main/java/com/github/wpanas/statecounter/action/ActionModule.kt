package com.github.wpanas.statecounter.action

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActionModule(private val application: Application) {

    @Provides
    @Singleton
    fun viewModel(): ActionViewModel {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(ActionViewModel::class.java)
    }
}