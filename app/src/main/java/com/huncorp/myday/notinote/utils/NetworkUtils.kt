@file:Suppress("DEPRECATION", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.huncorp.myday.notinote.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

object NetworkUtils {

    @SuppressLint("MissingPermission")
    fun connection(context: Context?): Boolean {
        if(context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }

}