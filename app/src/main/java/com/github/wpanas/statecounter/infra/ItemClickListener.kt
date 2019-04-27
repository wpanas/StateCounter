package com.github.wpanas.statecounter.infra

import android.view.View

interface ItemClickListener {
    fun onItemClicked(view: View, position: Int)
}