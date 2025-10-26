package com.xandone.twandroid.repository


import com.xandone.twandroid.db.AppDatabase
import com.xandone.twandroid.db.entity.WordCEt4
import com.xandone.twandroid.db.entity.WordCEt6

/**
 * @author: xiao
 * created on: 2025/10/22 16:33
 * description:
 */
class WordRepository {

    suspend fun getWordCEt4ByPage(page: Int, pageSize: Int): List<WordCEt4> {
        val validPage = if (page < 1) 1 else page
        val validPageSize = if (pageSize < 1) 20 else pageSize
        // 计算偏移量：跳过 (page-1)*pageSize 条数据
        val offset = (validPage - 1) * validPageSize

        return AppDatabase.getInstance().wordCEt4Dao().getWordCEt4ByPage(validPageSize, offset)
    }

    suspend fun getWordCEt6ByPage(page: Int, pageSize: Int): List<WordCEt6> {
        val validPage = if (page < 1) 1 else page
        val validPageSize = if (pageSize < 1) 20 else pageSize
        val offset = (validPage - 1) * validPageSize

        return AppDatabase.getInstance().wordCEt6Dao().getWordCEt6ByPage(validPageSize, offset)
    }

    suspend fun checkDataExists(): Int {
        return AppDatabase.getInstance().wordCEt4Dao().count()
    }


}