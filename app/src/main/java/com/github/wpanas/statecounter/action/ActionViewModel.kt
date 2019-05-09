package com.github.wpanas.statecounter.action

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActionViewModel(private val repository: ActionRepository) : ViewModel() {
    val allActions: LiveData<List<Action>> = repository.allActions

    fun save(action: Action) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(action)
    }

    fun delete(action: Action) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(action)
    }

    class Factory(private val repository: ActionRepository) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ActionViewModel(repository) as T
        }
    }
}