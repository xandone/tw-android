package com.xandone.twandroid.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.Kaoyan
import com.xandone.twandroid.db.entity._926Entity

/**
 * @author: xiao
 * created on: 2025/10/22 16:16
 * description:
 */
@Dao
interface KaoyanDao {
    @Query("select * from ${DBInfo.TABLE_KAOYAN_3_T} order by wid asc limit :pageSize offset :offset")
    suspend fun loadDB(pageSize: Int, offset: Int): List<Kaoyan>

}