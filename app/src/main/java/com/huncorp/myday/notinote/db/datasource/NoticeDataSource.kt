package com.huncorp.myday.notinote.db.datasource

import com.huncorp.myday.notinote.db.dao.NoticeDao
import com.huncorp.myday.notinote.model.Notice
import javax.inject.Inject

interface NoticeDataSource {
    suspend fun getAll(): List<Notice>
    suspend fun getById(id: Long): Notice
    suspend fun insert(notice: Notice)
    suspend fun update(notice: Notice)

    suspend fun delete(notice: Notice)
}