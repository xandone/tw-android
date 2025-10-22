package com.xandone.twandroid

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.xandone.twandroid.db.dao.WordCEt4Dao
import com.xandone.twandroid.db.entity.WordCEt4
import kotlinx.coroutines.flow.Flow

/**
 * @author: xiao
 * created on: 2025/10/22 16:33
 * description:
 */
class WordRepository(private val dao: WordCEt4Dao) {
    fun getPagedWordCEt4(): Flow<PagingData<WordCEt4>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // 每页数量
                prefetchDistance = 5, // 预加载触发距离
                enablePlaceholders = false // 不使用占位符
            ),
            pagingSourceFactory = { dao.getWordCEt4ByPage() }
        ).flow
    }

     fun checkDataExists(): Boolean {
        return dao.count() > 0
    }
}