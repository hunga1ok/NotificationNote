package com.huncorp.myday.notinote.base

import android.os.SystemClock
import android.view.View

class SafeClickListener(
    private var defaultInterval: Int = 1000,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        onSafeCLick(v)
        lastTimeClicked = SystemClock.elapsedRealtime()
    }
}

fun View.onClick(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun View.onClick(interval: Int, onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener(interval) {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun View.onClickIf(enable: Boolean, onSafeClick: (View) -> Unit) {
    if (!enable) return removeOnClick()
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun View.removeOnClick() = setOnClickListener(null)

fun onClick(vararg views: View?, onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    views.forEach { view -> view?.setOnClickListener(safeClickListener)
    }
}