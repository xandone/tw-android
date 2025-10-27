package com.xandone.twandroid.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.NceNew4
import com.xandone.twandroid.db.entity.Oxford3000

/**
 * @author: xiao
 * created on: 2025/10/22 16:16
 * description:
 */
@Dao
interface Oxford3000Dao {
    @Query("select * from ${DBInfo.TABLE_OXFORD3000} order by wid asc limit :pageSize offset :offset")
    suspend fun loadDB(pageSize: Int, offset: Int): List<Oxford3000>

}