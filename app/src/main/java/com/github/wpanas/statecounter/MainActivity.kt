package com.github.wpanas.statecounter

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.wpanas.statecounter.action.Action
import com.github.wpanas.statecounter.action.ActionItemAdapter
import com.github.wpanas.statecounter.action.ActionViewModel
import com.github.wpanas.statecounter.counter.CounterChanger
import com.github.wpanas.statecounter.counter.CounterViewModel
import com.github.wpanas.statecounter.infra.ItemClickListener
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), ItemClickListener, CounterChanger {
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var actionViewModelFactory: ActionViewModel.Factory

    @Inject
    lateinit var counterViewModel: CounterViewModel

    private val actionViewModel: ActionViewModel by lazy {
        return@lazy ViewModelProviders.of(this, actionViewModelFactory).get(ActionViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.action_recycler)
        val actionItemAdapter = ActionItemAdapter(this)
        recyclerView.apply {
            adapter = actionItemAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
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

    override fun saveState(view: View) {
        actionViewModel.save(Action.of(counterViewModel.data.value ?: 0))
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector
}
