package com.github.wpanas.statecounter.counter

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.wpanas.statecounter.R
import com.github.wpanas.statecounter.action.Action
import com.github.wpanas.statecounter.action.ActionClickListener
import com.github.wpanas.statecounter.action.ActionItemAdapter
import com.github.wpanas.statecounter.action.ActionViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class CounterActivity : DaggerAppCompatActivity(), ActionClickListener, CounterChanger {

    @Inject
    lateinit var actionViewModelFactory: ActionViewModel.Factory

    @Inject
    lateinit var counterViewModel: CounterViewModel

    private val actionViewModel: ActionViewModel by lazy {
        return@lazy ViewModelProviders.of(this, actionViewModelFactory).get(ActionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.action_recycler)
        val actionItemAdapter = ActionItemAdapter(this)
        recyclerView.apply {
            adapter = actionItemAdapter
            layoutManager = LinearLayoutManager(this@CounterActivity)
        }

        actionViewModel.allActions.observe(this, Observer {
            actionItemAdapter.setData(it)
        })
    }

    override fun onItemClicked(view: View, position: Int) {
        val action = actionViewModel.allActions.value?.get(position)

        if (action != null) {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.delete_state_confirmation, action.value))
                setPositiveButton(android.R.string.yes) { _, _ ->
                    actionViewModel.delete(action)
                }
                setNegativeButton(android.R.string.no, null)
                show()
            }
        }
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
