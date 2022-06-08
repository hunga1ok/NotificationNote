package com.huncorp.myday.notinote.base

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.viewbinding.BuildConfig
import com.huncorp.myday.notinote.R
import com.huncorp.myday.notinote.utils.KeyboardUtils
import com.huncorp.myday.notinote.utils.alert
import com.huncorp.myday.notinote.utils.fatalError
import com.huncorp.myday.notinote.utils.toast

abstract class BaseActivity : AppCompatActivity(), BaseView {

    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onMessage(message: String?) {
        log(message.orEmpty())
        toast(message.orEmpty())
    }

    override fun onMessage(stringResId: Int) {
        log(getString(stringResId))
        toast(stringResId)
    }

    override fun onAlert(message: String) {
        alert(message)
    }

    override fun onAlert(title: String, message: String) {
        alert(title, message)
    }

    override fun isNetworkConnect(): Boolean {
        return false
    }

    override fun hideKeyboard() {
        return KeyboardUtils.hide(this)
    }

    override fun log(message: Any?) {
        if (!BuildConfig.DEBUG) return
        if (message?.toString()?.length ?: 0 > 2000) longLog(message)
        else Log.d(this.localClassName, message?.toString() ?: "")
    }

    override fun log(message: Throwable?) {
        if (!BuildConfig.DEBUG) return
        if (message?.localizedMessage?.length ?: 0 > 2000) longLog(message)
        Log.e(this.localClassName, message?.localizedMessage, message)
    }

    override fun longLog(any: Any?) {
        if (!BuildConfig.DEBUG) return
        val message = any.toString()
        val maxLogSize = 2000
        for (i in 0..message.length / maxLogSize) {
            val start = i * maxLogSize
            var end = (i + 1) * maxLogSize
            end = if (end > message.length) message.length else end
            Log.d(this.localClassName, message.substring(start, end))
        }
    }

    override fun onUnknownError(errorMessage: String) {
        alert("Network Error", errorMessage)
    }

    override fun onNetworkTimeout() {
        alert("Connection Timeout", "Oops Connection Timeout")
    }

    override fun onNetworkError(message: String) {
        alert("Network Error", message)
    }

    override fun fatalError(message: String) {
        fatalError("Fatal Error\n$message", getString(R.string.BACK)) { finish() }
    }


    override fun getCurrentContext() = this

    override fun onNewIntent(intent: Intent?) {
        intent?.let {
            Log.d("FCM", intent.toUri(0).replace(";", "\n"))
        }
        super.onNewIntent(intent)
    }

}