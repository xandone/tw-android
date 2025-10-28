package com.xandone.twandroid.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.ErrorWord

/**
 * @author: xiao
 * created on: 2025/10/23 15:11
 * description:
 */
@Dao
interface ErrorWordDao {
    @Insert
    suspend fun insertErrorWord(errorWord: ErrorWord)

    @Update
    suspend fun updateErrorWord(errorWord: ErrorWord)

    @Query("select * from ${DBInfo.TABLE_ERROR_WORD} where errorwid = :errorwid and errortable = :table limit 1")
    suspend fun getErrorWordById(errorwid: Int, table: String): ErrorWord?

}