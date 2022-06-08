package com.huncorp.myday.notinote.manager

import com.huncorp.myday.notinote.db.repository.LocalRepository
import com.huncorp.myday.notinote.model.Notice
import javax.inject.Inject

class NotiManager @Inject constructor(
    private val local: LocalRepository
) {
    suspend fun getAll() = local.getAll()
    suspend fun getById(id: Long) = local.getById(id)
    suspend fun insert(notice: Notice) = local.insert(notice)
    suspend fun update(notice: Notice) = local.update(notice)
    suspend fun delete(notice: Notice) = local.delete(notice)
}