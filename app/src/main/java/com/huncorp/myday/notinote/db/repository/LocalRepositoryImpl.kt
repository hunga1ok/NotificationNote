package com.huncorp.myday.notinote.db.repository

import com.huncorp.myday.notinote.db.datasource.NoticeDataSource
import com.huncorp.myday.notinote.model.Notice
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val noticeDataSource: NoticeDataSource
) : LocalRepository {
    override suspend fun getAll() = noticeDataSource.getAll()

    override suspend fun getById(id: Long) = noticeDataSource.getById(id)

    override suspend fun insert(title: String, content: String) =
        noticeDataSource.insert(title, content)

    override suspend fun update(notice: Notice) = noticeDataSource.update(notice)

    override suspend fun delete(notice: Notice) = noticeDataSource.delete(notice)
}