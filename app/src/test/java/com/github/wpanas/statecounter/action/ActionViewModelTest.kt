package com.github.wpanas.statecounter.action

import androidx.lifecycle.MutableLiveData
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Test

class ActionViewModelTest {
    @Test
    fun `should save action`() {
        // given
        val repository = mockk<ActionRepository>(relaxed = true)
        val viewModel = ActionViewModel(repository)
        val action = Action.of(5)

        // when
        viewModel.save(action)

        // then
        excludeRecords {
            repository.allActions
        }

        verify {
            repository.insert(action)
        }

        confirmVerified(repository)
    }

    @Test
    fun `should delete action`() {
        // given
        val repository = mockk<ActionRepository>(relaxed = true)
        val viewModel = ActionViewModel(repository)
        val action = Action.of(5)

        // when
        viewModel.delete(action)

        // then
        excludeRecords {
            repository.allActions
        }

        verify {
            repository.delete(action)
        }

        confirmVerified(repository)
    }

    @Test
    fun `should return all actions`() {
        // given
        val repository = mockk<ActionRepository>()
        val liveData = MutableLiveData<List<Action>>()
        every { repository.allActions } returns liveData
        val viewModel = ActionViewModel(repository)

        // when
        val allActions = viewModel.allActions

        // then
        assertEquals(allActions, liveData)
    }
}