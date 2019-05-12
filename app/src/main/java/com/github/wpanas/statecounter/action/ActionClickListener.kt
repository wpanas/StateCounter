package com.github.wpanas.statecounter.action

import android.view.View

interface ActionClickListener {
    fun onItemClicked(view: View, position: Int)
}