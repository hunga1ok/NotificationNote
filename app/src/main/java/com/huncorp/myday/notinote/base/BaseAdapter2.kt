package com.huncorp.myday.notinote.base

import android.view.View
import androidx.annotation.LayoutRes
import de.dlyt.yanndroid.oneui.view.RecyclerView

/**
 * Created by korneliussendy on 25/07/20,
 * at 08.20.
 * ajaib
 */
abstract class BaseAdapter2<I>(@LayoutRes var c: Int) :
    BaseAdapter<I, BaseAdapter2.BaseViewHolder>(c) {
    override fun createView(view: View, viewType: Int) = BaseViewHolder(view)
    class BaseViewHolder(val v: View) : RecyclerView.ViewHolder(v)
}