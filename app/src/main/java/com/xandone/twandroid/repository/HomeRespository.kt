package com.xandone.twandroid.repository

import com.xandone.twandroid.db.AppDatabase
import com.xandone.twandroid.db.entity.WordHomeEntity

/**
 * @author: xiao
 * created on: 2025/10/28 10:36
 * description:
 */
class HomeRespository {

    suspend fun insertHomeWordList(list: List<WordHomeEntity>) {
        return AppDatabase.getInstance().wordHomeDao().insertHomeWordList(list)
    }

    suspend fun loadHomeData(): List<WordHomeEntity> {
        return AppDatabase.getInstance().wordHomeDao().loadDB()
    }

    suspend fun hasData(): Boolean {
        return AppDatabase.getInstance().wordHomeDao().count() > 0
    }
}