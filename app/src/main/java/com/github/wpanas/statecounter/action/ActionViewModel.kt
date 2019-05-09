package com.github.wpanas.statecounter.action

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ActionViewModel(private val repository: ActionRepository) : ViewModel() {
    val allActions: LiveData<List<Action>> = repository.allActions

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

    class Factory(private val repository: ActionRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ActionViewModel(repository) as T
        }
    }
}