package com.xandone.twandroid.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.DuolinguoB2

/**
 * @author: xiao
 * created on: 2025/10/22 16:16
 * description:
 */
@Dao
interface DuolinguoB2Dao {
    @Query("select * from ${DBInfo.TABLE_DUOLINGO_VOCABULARY_B2} order by wid asc limit :pageSize offset :offset")
    suspend fun loadDB(pageSize: Int, offset: Int): List<DuolinguoB2>

}