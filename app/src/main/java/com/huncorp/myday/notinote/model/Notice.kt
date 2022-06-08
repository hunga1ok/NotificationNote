package com.huncorp.myday.notinote.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notice_table")
@Parcelize
data class Notice(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var title: String,
    var content: String
) : Parcelable