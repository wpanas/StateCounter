package com.github.wpanas.statecounter.action

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActionModule(private val application: Application) {

    @Provides
    @Singleton
    fun viewModelFactory(actionRepository: ActionRepository): ActionViewModel.Factory {
        return ActionViewModel.Factory(actionRepository)
    }

    @Provides
    @Singleton
    fun repository(): ActionRepository {
        val actionDao = ActionRoomDatabase.getDatabase(application).actionDao()
        return ActionRepository(actionDao)
    }

    @Provides
    @Singleton
    fun actionDialog(actionViewModelFactory: ActionViewModel.Factory): ActionDialog {
        return ActionDialog(actionViewModelFactory)
    }
}