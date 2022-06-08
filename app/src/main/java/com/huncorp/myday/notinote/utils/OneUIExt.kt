package com.huncorp.myday.notinote.utils

import androidx.appcompat.app.AppCompatActivity
import de.dlyt.yanndroid.oneui.sesl.recyclerview.LinearLayoutManager
import de.dlyt.yanndroid.oneui.view.RecyclerView

fun AppCompatActivity.setupRecyclerViewOneUI(
    rv: RecyclerView,
    adapter: RecyclerView.Adapter<*>,
    animate: Boolean = false
): LinearLayoutManager {
    val manager = LinearLayoutManager(this)
    rv.layoutManager = manager
    rv.adapter = adapter
    if (!animate) rv.itemAnimator = null
    rv.seslSetGoToTopEnabled(true)
    rv.seslSetFillBottomEnabled(true)
    rv.seslSetLastRoundedCorner(false)
    return manager
}

fun AppCompatActivity.setupRecyclerViewOneUI(rv: RecyclerView, adapter: RecyclerView.Adapter<*>) =
    setupRecyclerViewOneUI(rv, adapter, false)