package com.huncorp.myday.notinote.db.repository

import com.huncorp.myday.notinote.model.Notice

interface LocalRepository {
    suspend fun getAll(): List<Notice>
    suspend fun getById(id: Long): Notice
    suspend fun insert(title: String, content: String): Long
    suspend fun update(notice: Notice)

    suspend fun delete(notice: Notice)
}