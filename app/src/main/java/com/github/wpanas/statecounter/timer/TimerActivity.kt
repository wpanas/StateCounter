package com.github.wpanas.statecounter.timer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.wpanas.statecounter.R
import com.github.wpanas.statecounter.action.Action
import com.github.wpanas.statecounter.action.Action.ActionType.TIMING
import com.github.wpanas.statecounter.action.ActionDialog
import com.github.wpanas.statecounter.action.ActionListFragment.ActionListFragmentInteractionListener
import com.github.wpanas.statecounter.action.ActionViewModel
import com.github.wpanas.statecounter.counter.CounterFragment.CounterFragmentInteractionListener
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class TimerActivity : DaggerAppCompatActivity(), CounterFragmentInteractionListener,
    ActionListFragmentInteractionListener {

    @Inject
    lateinit var timerViewModel: TimerViewModel

    @Inject
    lateinit var actionDialog: ActionDialog

    @Inject
    lateinit var actionViewModelFactory: ActionViewModel.Factory

    private val actionDialogBuilder: ActionDialog.Builder by lazy {
        actionDialog.Builder(this)
    }

    private val actionViewModel: ActionViewModel by lazy {
        return@lazy ViewModelProviders.of(this, actionViewModelFactory).get(ActionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        timerViewModel.reset()
    }

    @Suppress("UNUSED_PARAMETER")
    fun start(view: View) {
        val counter = timerViewModel.counter
        timerViewModel.start {
            actionViewModel.save(Action.of(counter, TIMING))
            Toast.makeText(this, "Finished after ${counter}s", Toast.LENGTH_LONG).show()
        }
    }

    override fun incrementState(view: View) {
        timerViewModel.increment()
    }

    override fun decrementState(view: View) {
        timerViewModel.decrement()
    }

    override fun onActionClicked(view: View, position: Int) {
        val action = actionViewModel.timingActions.value?.get(position)

        if (action != null) {
            actionDialogBuilder.delete(action).show()
        }
    }

    override fun registerActions(observer: Observer<List<Action>>) {
        actionViewModel.timingActions.observe(this, observer)
    }
}
