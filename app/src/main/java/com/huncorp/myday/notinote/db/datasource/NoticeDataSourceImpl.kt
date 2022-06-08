package com.huncorp.myday.notinote.db.datasource

import com.huncorp.myday.notinote.db.dao.NoticeDao
import com.huncorp.myday.notinote.model.Notice
import javax.inject.Inject

class NoticeDataSourceImpl @Inject constructor(
    private val noticeDao: NoticeDao
): NoticeDataSource {
    override suspend fun getAll() = noticeDao.getAllNotice()
    override suspend fun getById(id: Long) = noticeDao.getNoticeById(id)
    override suspend fun insert(notice: Notice) = noticeDao.insertNotice(notice.title, notice.content)
    override suspend fun update(notice: Notice) =
        noticeDao.updateNoticeById(notice.id, notice.title, notice.content)

    override suspend fun delete(notice: Notice) = noticeDao.deleteNoticeById(notice.id)
}