package com.huncorp.myday.notinote.di

import com.huncorp.myday.notinote.db.datasource.NoticeDataSource
import com.huncorp.myday.notinote.db.datasource.NoticeDataSourceImpl
import com.huncorp.myday.notinote.db.repository.LocalRepository
import com.huncorp.myday.notinote.db.repository.LocalRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class LocalModule {
    @Singleton
    @Binds
    abstract fun bindLocalRepository(impl: LocalRepositoryImpl): LocalRepository

    @Singleton
    @Binds
    abstract fun bindNoticeDataSource(impl: NoticeDataSourceImpl): NoticeDataSource
}