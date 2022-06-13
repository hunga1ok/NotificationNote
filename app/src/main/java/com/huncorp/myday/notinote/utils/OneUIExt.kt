package com.huncorp.myday.notinote.utils

import androidx.appcompat.app.AppCompatActivity
import de.dlyt.yanndroid.oneui.sesl.recyclerview.DividerItemDecoration
import de.dlyt.yanndroid.oneui.sesl.recyclerview.LinearLayoutManager
import de.dlyt.yanndroid.oneui.view.RecyclerView

fun AppCompatActivity.setupRecyclerViewOneUI(
    rv: RecyclerView,
    adapter: RecyclerView.Adapter<*>,
    animate: Boolean = false,
    divider: Boolean = false
): LinearLayoutManager {
    val manager = LinearLayoutManager(this)
    rv.layoutManager = manager
    rv.adapter = adapter
    if (!animate) rv.itemAnimator = null
    if (divider) {
        val divider = DividerItemDecoration(this, manager.orientation)
        rv.addItemDecoration(divider)
    }
    rv.seslSetGoToTopEnabled(true)
    rv.seslSetFillBottomEnabled(true)
    rv.seslSetLastRoundedCorner(false)
    return manager
}

fun AppCompatActivity.setupRecyclerViewOneUI(rv: RecyclerView, adapter: RecyclerView.Adapter<*>) =
    setupRecyclerViewOneUI(rv, adapter, false)