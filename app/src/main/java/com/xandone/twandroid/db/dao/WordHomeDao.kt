package com.xandone.twandroid.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.WordHomeEntity

@Dao
interface WordHomeDao {
    @Insert
    suspend fun insertHomeWordList(homeWords: List<WordHomeEntity>)

    @Update
    suspend fun updateHomeWord(homeWord: WordHomeEntity)

    @Query("select * from ${DBInfo.TABLE_WORD_HOME} where wid = :wid limit 1")
    suspend fun getHomeWordById(wid: Int): WordHomeEntity?

    @Query("select count(*) from ${DBInfo.TABLE_WORD_HOME}")
    suspend fun count(): Int

    @Query("select * from ${DBInfo.TABLE_WORD_HOME} order by wid asc")
    suspend fun loadDB(): List<WordHomeEntity>

    @Query("select * from ${DBInfo.TABLE_WORD_HOME} where wid = :table limit 1")
    suspend fun getHomeWordByTableName(table: String): WordHomeEntity?

}