package com.xandone.twandroid.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.NceNew2

/**
 * @author: xiao
 * created on: 2025/10/22 16:16
 * description:
 */
@Dao
interface NceNew2Dao {
    @Query("select * from ${DBInfo.TABLE_NCE_NEW_2} order by wid asc limit :pageSize offset :offset")
    suspend fun loadDB(pageSize: Int, offset: Int): List<NceNew2>

}