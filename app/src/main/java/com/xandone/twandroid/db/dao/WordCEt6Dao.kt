package com.xandone.twandroid.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.WordCEt6

/**
 * @author: xiao
 * created on: 2025/10/22 16:16
 * description:
 */
@Dao
interface WordCEt6Dao {
    @Query("select * from ${DBInfo.TABLE_CET6} order by wid asc limit :pageSize offset :offset")
    suspend fun loadDB(pageSize: Int, offset: Int): List<WordCEt6>

    @Query("select count(*) from ${DBInfo.TABLE_CET6}")
    suspend fun count(): Int

}