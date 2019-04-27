package com.github.wpanas.statecounter

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.wpanas.statecounter.action.Action
import com.github.wpanas.statecounter.action.ActionItemAdapter
import com.github.wpanas.statecounter.action.ActionModule
import com.github.wpanas.statecounter.action.ActionViewModel
import com.github.wpanas.statecounter.counter.CounterViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
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
        val actionItemAdapter = ActionItemAdapter()
        recyclerView.apply {
            adapter = actionItemAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        actionModel.allActions.observe(this, Observer {
            actionItemAdapter.setData(it)
        })
    }

    fun incrementState(view: View) {
        counterModel.increment()
    }

    fun decrementState(view: View) {
        counterModel.decrement()
    }

    fun saveState(view: View) {
        actionModel.save(Action.of(counterModel.getData().value ?: 0))
    }
}
