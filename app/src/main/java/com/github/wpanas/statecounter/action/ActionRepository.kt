package com.github.wpanas.statecounter.action

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class ActionRepository(private val actionDao: ActionDao) {
    val allActions: LiveData<List<Action>> = actionDao.getAllActions()

    @WorkerThread
    fun insert(action: Action) {
        actionDao.insert(action)
    }

    @WorkerThread
    fun delete(action: Action) {
        actionDao.delete(action)
    }
}