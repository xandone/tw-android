package com.xandone.twandroid.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.WordCEt4

/**
 * @author: xiao
 * created on: 2025/10/22 16:16
 * description:
 */
@Dao
interface WordCEt4Dao {
    @Query("select * from cet4_words order by wid asc limit :pageSize offset :offset")
    suspend fun loadDB(pageSize: Int, offset: Int): List<WordCEt4>

    @Query("select count(*) from ${DBInfo.TABLE_CET4}")
    suspend fun count(): Int

}