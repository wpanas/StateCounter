package com.github.wpanas.statecounter.action

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.github.wpanas.statecounter.action.Action.ActionType.COUNTING
import com.github.wpanas.statecounter.action.Action.ActionType.TIMING

class ActionRepository(private val actionDao: ActionDao) {
    val allActions: LiveData<List<Action>> = actionDao.getAllActions()
    val countingActions: LiveData<List<Action>> = actionDao.getActionsByType(COUNTING)
    val timingActions: LiveData<List<Action>> = actionDao.getActionsByType(TIMING)

    @WorkerThread
    fun insert(action: Action) {
        actionDao.insert(action)
    }

    @WorkerThread
    fun delete(action: Action) {
        actionDao.delete(action)
    }
}