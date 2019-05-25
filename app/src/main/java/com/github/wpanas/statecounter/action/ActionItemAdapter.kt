package com.github.wpanas.statecounter.action

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.wpanas.statecounter.R
import kotlinx.android.synthetic.main.action_item.view.*

class ActionItemAdapter(private val actionClickListener: ActionClickListener) :
    RecyclerView.Adapter<ActionItemAdapter.ActionHolder>() {
    internal var actions: List<Action> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: ActionHolder, position: Int) {
        holder.itemView.action_item_text.text = actions[position].value.toString()
        holder.itemView.action_item_card.setOnClickListener {
            actionClickListener.onItemClicked(it, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.action_item, parent, false)

        return ActionHolder(view)
    }

    override fun getItemCount() = actions.size

    class ActionHolder(view: View) : RecyclerView.ViewHolder(view)

    interface ActionClickListener {
        fun onItemClicked(view: View, position: Int)
    }
}
