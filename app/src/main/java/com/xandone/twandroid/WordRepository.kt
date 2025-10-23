package com.xandone.twandroid


import com.xandone.twandroid.db.dao.WordCEt4Dao
import com.xandone.twandroid.db.entity.WordCEt4

/**
 * @author: xiao
 * created on: 2025/10/22 16:33
 * description:
 */
class WordRepository(private val dao: WordCEt4Dao) {

    suspend fun getWordCEt4ByPage(page: Int, pageSize: Int): List<WordCEt4> {
        val validPage = if (page < 1) 1 else page
        val validPageSize = if (pageSize < 1) 20 else pageSize
        // 计算偏移量：跳过 (page-1)*pageSize 条数据
        val offset = (validPage - 1) * validPageSize

        return dao.getWordCEt4ByPage(validPageSize, offset)
    }

    suspend fun checkDataExists(): Int {
        return dao.count()
    }


}