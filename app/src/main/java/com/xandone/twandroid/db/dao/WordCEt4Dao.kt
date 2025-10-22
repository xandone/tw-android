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
    @Query("select * from cet4_words order by wid asc")
    fun getWordCEt4ByPage(): PagingSource<Int, WordCEt4>

    @Query("SELECT COUNT(*) FROM cet4_words")
    fun count(): Int
}