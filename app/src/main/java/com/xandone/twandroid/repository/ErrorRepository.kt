package com.xandone.twandroid.repository

import com.xandone.twandroid.db.dao.ErrorWordDao
import com.xandone.twandroid.db.entity.BaseWordEntity
import com.xandone.twandroid.db.entity.ErrorWord

/**
 * @author: xiao
 * created on: 2025/10/23 15:33
 * description:
 */
class ErrorRepository(private val dao: ErrorWordDao) {
    suspend fun insertErrorWord(errorWord: ErrorWord) {
        return dao.insertErrorWord(errorWord)
    }

    suspend fun updateErrorWord(errorWord: ErrorWord) {
        return dao.updateErrorWord(errorWord)
    }

    suspend fun getErrorWordById(errorwid: Int, table: String): ErrorWord? {
        return dao.getErrorWordById(errorwid, table)
    }

    suspend fun loadDB(page: Int, pageSize: Int): List<ErrorWord> {
        val validPage = if (page < 1) 1 else page
        val validPageSize = if (pageSize < 1) 20 else pageSize
        val offset = (validPage - 1) * validPageSize

        return dao.loadDB(validPageSize, offset)
    }

}