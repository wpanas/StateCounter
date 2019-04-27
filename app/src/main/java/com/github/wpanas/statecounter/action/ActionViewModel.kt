package com.github.wpanas.statecounter.action

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ActionViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ActionRepository
    val allActions: LiveData<List<Action>>

    init {
        val actionDao = ActionRoomDatabase.getDatabase(application).actionDao()
        repository = ActionRepository(actionDao)
        allActions = repository.allActions
    }

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    fun save(action: Action) = scope.launch(Dispatchers.IO) {
        repository.insert(action)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun delete(action: Action) = scope.launch(Dispatchers.IO) {
        repository.delete(action)
    }
}