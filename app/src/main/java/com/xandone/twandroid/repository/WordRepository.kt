package com.xandone.twandroid.repository


import com.xandone.twandroid.db.AppDatabase
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.BaseWordEntity

/**
 * @author: xiao
 * created on: 2025/10/22 16:33
 * description:
 */
class WordRepository {

    suspend fun loadDB(table: String, page: Int, pageSize: Int): List<BaseWordEntity> {
        val validPage = if (page < 1) 1 else page
        val validPageSize = if (pageSize < 1) 20 else pageSize
        // 计算偏移量：跳过 (page-1)*pageSize 条数据
        val offset = (validPage - 1) * validPageSize

        return when (table) {
            DBInfo.TABLE_CET4 -> AppDatabase.getInstance().wordCEt4Dao()
                .loadDB(validPageSize, offset)

            DBInfo.TABLE_CET6 -> AppDatabase.getInstance().wordCEt6Dao()
                .loadDB(validPageSize, offset)

            DBInfo.TABLE_926 -> AppDatabase.getInstance()._926Dao()
                .loadDB(validPageSize, offset)

            else -> AppDatabase.getInstance().wordCEt4Dao().loadDB(validPageSize, offset)
        }
    }

    suspend fun checkDataExists(): Int {
        return AppDatabase.getInstance().wordCEt4Dao().count()
    }


}