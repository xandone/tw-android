package com.xandone.twandroid.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.NceNew4
import com.xandone.twandroid.db.entity.Oxford3000
import com.xandone.twandroid.db.entity.Voa

/**
 * @author: xiao
 * created on: 2025/10/22 16:16
 * description:
 */
@Dao
interface VoaDao {
    @Query("select * from ${DBInfo.TABLE_VOA} order by wid asc limit :pageSize offset :offset")
    suspend fun loadDB(pageSize: Int, offset: Int): List<Voa>

}