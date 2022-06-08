package com.huncorp.myday.notinote.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.huncorp.myday.notinote.db.dao.NoticeDao
import com.huncorp.myday.notinote.model.Notice

@Database(
    entities = [Notice::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noticeDao(): NoticeDao
}