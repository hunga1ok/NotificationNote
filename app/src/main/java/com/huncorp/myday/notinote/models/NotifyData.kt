package com.huncorp.myday.notinote.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NotifyData(val title: String, val desc: String) : Parcelable