package com.github.wpanas.statecounter

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.wpanas.statecounter.action.Action
import com.github.wpanas.statecounter.action.ActionItemAdapter
import com.github.wpanas.statecounter.action.ActionModule
import com.github.wpanas.statecounter.action.ActionViewModel
import com.github.wpanas.statecounter.counter.CounterViewModel
import com.github.wpanas.statecounter.infra.ItemClickListener
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ItemClickListener {
    @Inject lateinit var counterModel: CounterViewModel
    @Inject lateinit var actionModel: ActionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerMainComponent.builder()
            .actionModule(ActionModule(this))
            .build()
            .inject(this)

        initCounter()
        initRecyclerView()
    }

    private fun initCounter() {
        val counter = findViewById<TextView>(R.id.counter)
        counterModel.getData().observe(this, Observer {
            counter.text = it.toString()
        })
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.action_recycler)
        val actionItemAdapter = ActionItemAdapter(this)
        recyclerView.apply {
            adapter = actionItemAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        actionModel.allActions.observe(this, Observer {
            actionItemAdapter.setData(it)
        })
    }

    @Suppress("UNUSED_PARAMETER")
    fun incrementState(view: View) {
        counterModel.increment()
    }

    @Suppress("UNUSED_PARAMETER")
    fun decrementState(view: View) {
        counterModel.decrement()
    }

    @Suppress("UNUSED_PARAMETER")
    fun saveState(view: View) {
        actionModel.save(Action.of(counterModel.getData().value ?: 0))
    }

    override fun onItemClicked(view: View, position: Int) {
        val action = actionModel.allActions.value?.get(position)

        if (action != null) {
            AlertDialog.Builder(this).let {
                it.setTitle(getString(R.string.delete_state_confirmation, action.value))

                it.setPositiveButton(android.R.string.yes) { dialog, which ->
                    actionModel.delete(action)
                }

                it.setNegativeButton(android.R.string.no, null)

                it.show()
            }
        }
    }
}
