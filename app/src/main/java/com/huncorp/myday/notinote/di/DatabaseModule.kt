package com.huncorp.myday.notinote.di

import android.content.Context
import androidx.room.Room
import com.huncorp.myday.notinote.db.AppDatabase
import com.huncorp.myday.notinote.db.dao.NoticeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "notify_note.db"
        ).build()
    }

    @Provides
    fun provideNoticeDao(database: AppDatabase): NoticeDao {
        return database.noticeDao()
    }
}