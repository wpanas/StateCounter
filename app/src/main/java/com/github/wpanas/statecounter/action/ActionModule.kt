package com.github.wpanas.statecounter.action

import androidx.lifecycle.ViewModelProvider
import com.github.wpanas.statecounter.MainActivity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActionModule(private val mainActivity: MainActivity) {

    @Provides
    @Singleton
    fun viewModel(): ActionViewModel {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(mainActivity.application).create(ActionViewModel::class.java)
    }
}