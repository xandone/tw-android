package com.xandone.twandroid.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.PracticeWord

@Dao
interface PracticeWordDao {
    @Insert
    suspend fun insertPracticeWord(practiceWord: PracticeWord)

    @Update
    suspend fun updatePracticeWord(practiceWord: PracticeWord)

    @Query("select * from ${DBInfo.TABLE_PRACTICE_WOED} where practicewid = :practicewid limit 1")
    suspend fun getPracticeWordById(practicewid: Int): PracticeWord?
}