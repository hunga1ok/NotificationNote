package com.huncorp.myday.notinote.base

import android.content.Context
import androidx.annotation.StringRes

interface BaseView {
    fun onMessage(message: String?)
    fun onMessage(@StringRes stringResId: Int)
    fun onAlert(message: String)
    fun onAlert(title: String, message: String)
    fun isNetworkConnect(): Boolean
    fun hideKeyboard()
    fun log(message: Any?)
    fun log(message: Throwable?)
    fun longLog(any: Any?)
    fun onUnknownError(errorMessage: String)
    fun onNetworkTimeout()
    fun onNetworkError(message: String)
    fun fatalError(message: String)
    fun getCurrentContext(): Context?
}