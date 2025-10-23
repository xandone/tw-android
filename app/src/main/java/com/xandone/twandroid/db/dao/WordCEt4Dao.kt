package com.xandone.twandroid.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.xandone.twandroid.db.entity.WordCEt4

/**
 * @author: xiao
 * created on: 2025/10/22 16:16
 * description:
 */
@Dao
interface WordCEt4Dao {
    @Query("SELECT * FROM cet4_words ORDER BY wid ASC LIMIT :pageSize OFFSET :offset")
    suspend fun getWordCEt4ByPage(pageSize: Int, offset: Int): List<WordCEt4>

    @Query("select count(*) from cet4_words")
    suspend fun count(): Int

}