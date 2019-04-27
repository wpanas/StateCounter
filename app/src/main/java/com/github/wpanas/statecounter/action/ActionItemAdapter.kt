package com.github.wpanas.statecounter.action

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.wpanas.statecounter.R
import kotlinx.android.synthetic.main.action_item.view.*

class ActionItemAdapter : RecyclerView.Adapter<ActionItemAdapter.ActionHolder>() {
    private var actions: List<Action> = emptyList()

    override fun onBindViewHolder(holder: ActionHolder, position: Int) {
        holder.itemView.action_item_text.text = actions[position].value.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.action_item, parent, false)

        return ActionHolder(view)
    }

    fun setData(newActions: List<Action>) {
        actions = newActions
        notifyDataSetChanged()
    }

    override fun getItemCount() = actions.size

    class ActionHolder(view: View) : RecyclerView.ViewHolder(view)
}
