package com.github.wpanas.statecounter.action

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.github.wpanas.statecounter.R

class ActionDialog(private val actionViewModelFactory: ActionViewModel.Factory) {

    inner class Builder(activity: FragmentActivity) {
        private var context: Context = activity
        private var actionViewModel: ActionViewModel = ViewModelProviders.of(activity, actionViewModelFactory)
            .get(ActionViewModel::class.java)

        fun delete(action: Action): AlertDialog {
            val builder = AlertDialog.Builder(context).apply {
                setTitle(context.getString(R.string.delete_state_confirmation, action.value))
                setPositiveButton(android.R.string.yes) { _, _ ->
                    actionViewModel.delete(action)
                }
                setNegativeButton(android.R.string.no, null)
            }

            return builder.create()
        }
    }
}