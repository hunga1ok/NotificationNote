package com.huncorp.myday.notinote.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.huncorp.myday.notinote.model.Notice

@Dao
interface NoticeDao {
    @Query("SELECT * FROM notice_table")
    suspend fun getAllNotice(): List<Notice>
    
    @Query("SELECT * FROM notice_table WHERE id = :id")
    suspend fun getNoticeById(id: Long): Notice
    
    @Query("SELECT * FROM notice_table WHERE title LIKE :title")
    suspend fun getNoticeByTitle(title: String): List<Notice>
    
    @Query("SELECT * FROM notice_table WHERE content LIKE :content")
    suspend fun getNoticeByContent(content: String): List<Notice>
    
    @Query("SELECT * FROM notice_table WHERE title LIKE :query OR content LIKE :query")
    suspend fun getNoticeByQuery(query: String): List<Notice>
    
    //delete
    @Query("DELETE FROM notice_table WHERE id = :id")
    suspend fun deleteNoticeById(id: Long)
    
    //update
    @Query("UPDATE notice_table SET title = :title, content = :content WHERE id = :id")
    suspend fun updateNoticeById(id: Long, title: String, content: String)
    
    //insert
    @Query("INSERT INTO notice_table (title, content) VALUES (:title, :content)")
    suspend fun insertNotice(title: String, content: String): Long

    //delete all
    @Query("DELETE FROM notice_table")
    suspend fun deleteAllNotice()
    
    
}