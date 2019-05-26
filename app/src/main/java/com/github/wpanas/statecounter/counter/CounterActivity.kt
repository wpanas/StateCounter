package com.github.wpanas.statecounter.counter

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.wpanas.statecounter.R
import com.github.wpanas.statecounter.action.Action
import com.github.wpanas.statecounter.action.ActionDialog
import com.github.wpanas.statecounter.action.ActionListFragment.ActionListFragmentInteractionListener
import com.github.wpanas.statecounter.action.ActionViewModel
import com.github.wpanas.statecounter.counter.CounterFragment.CounterFragmentInteractionListener
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class CounterActivity : DaggerAppCompatActivity(), ActionListFragmentInteractionListener,
    CounterFragmentInteractionListener {

    @Inject
    lateinit var actionViewModelFactory: ActionViewModel.Factory

    @Inject
    lateinit var counterViewModel: CounterViewModel

    @Inject
    lateinit var actionDialog: ActionDialog

    private val actionDialogBuilder: ActionDialog.Builder by lazy {
        actionDialog.Builder(this)
    }

    private val actionViewModel: ActionViewModel by lazy {
        return@lazy ViewModelProviders.of(this, actionViewModelFactory).get(ActionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)
    }

    override fun onActionClicked(view: View, position: Int) {
        val action = actionViewModel.countingActions.value?.get(position)

        if (action != null) {
            actionDialogBuilder.delete(action).show()
        }
    }

    override fun registerActions(observer: Observer<List<Action>>) {
        actionViewModel.countingActions.observe(this, observer)
    }

    override fun incrementState(view: View) {
        counterViewModel.increment()
    }

    override fun decrementState(view: View) {
        counterViewModel.decrement()
    }

    @Suppress("UNUSED_PARAMETER")
    fun saveState(view: View) {
        actionViewModel.save(Action.of(counterViewModel.counter.value ?: 0))
    }
}
