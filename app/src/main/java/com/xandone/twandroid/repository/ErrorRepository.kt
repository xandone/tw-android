package com.xandone.twandroid.repository

import com.xandone.twandroid.db.dao.ErrorWordDao
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
}