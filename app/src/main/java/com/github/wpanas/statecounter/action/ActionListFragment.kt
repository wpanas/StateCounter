package com.github.wpanas.statecounter.action

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.github.wpanas.statecounter.R
import com.github.wpanas.statecounter.action.ActionItemAdapter.ActionClickListener
import dagger.android.support.DaggerFragment


class ActionListFragment : DaggerFragment(), ActionClickListener {
    private var listener: ActionListFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_action_list, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.action_recycler)
        val actionItemAdapter = ActionItemAdapter(this)
        recyclerView.apply {
            adapter = actionItemAdapter
            layoutManager = LinearLayoutManager(context)
        }

        listener?.registerActions(Observer {
            actionItemAdapter.actions = it
        })

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActionListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement ActionListFragmentInteractionListener")
        }
    }

    override fun onItemClicked(view: View, position: Int) {
        listener?.onActionClicked(view, position)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface ActionListFragmentInteractionListener {
        fun onActionClicked(view: View, position: Int)
        fun registerActions(observer: Observer<List<Action>>)
    }
}
