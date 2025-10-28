package com.xandone.twandroid.repository

import androidx.room.Transaction
import com.xandone.twandroid.db.AppDatabase
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.ErrorWord
import com.xandone.twandroid.db.entity.PracticeWord
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

    suspend fun insertPracticeWord(practiceWord: PracticeWord) {
        return AppDatabase.getInstance().practiceWordDao().insertPracticeWord(practiceWord)
    }

    suspend fun updatePracticeWord(practiceWord: PracticeWord) {
        return AppDatabase.getInstance().practiceWordDao().updatePracticeWord(practiceWord)
    }

    suspend fun getPracticeWordById(practiceWord: Int): PracticeWord? {
        return AppDatabase.getInstance().practiceWordDao().getPracticeWordById(practiceWord)
    }

    @Transaction
    suspend fun insertPracticeWordAndRefreshHomeData(practiceWord: PracticeWord) {
        insertPracticeWord(practiceWord)
        refreshHomeData(practiceWord.practicetable)
    }

    suspend fun refreshHomeData(tablename: String?) {
        if (tablename == null) {
            return
        }
        val wordHomeDao = AppDatabase.getInstance().wordHomeDao()
        val homeEntity = wordHomeDao.getHomeWordByTableName(tablename)
        if (homeEntity != null) {
            homeEntity.learnlen++
            wordHomeDao.updateHomeWord(homeEntity)
        }
    }
}