package com.xandone.twandroid.ui.mine.error

import androidx.lifecycle.ViewModel
import com.xandone.twandroid.db.DBInfo
import com.xandone.twandroid.db.entity.ErrorWord
import com.xandone.twandroid.repository.ErrorRepository

/**
 * @author: xiao
 * created on: 2025/10/31 11:35
 * description:
 */
class ErrorViewModel(private val repository: ErrorRepository) : ViewModel() {
    suspend fun loadData(): List<ErrorWord> {
        return repository.loadDB(1, 20)
    }

}