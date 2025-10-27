package com.xandone.twandroid.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xandone.twandroid.db.entity.BaseWordEntity
import com.xandone.twandroid.repository.WordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: xiao
 * created on: 2025/10/22 16:35
 * description:
 */
class CEt4ViewModel(private val repository: WordRepository) : ViewModel() {
    val pagedWordCEt4 = mutableListOf<BaseWordEntity>()

    val mCurrentWordIndex = MutableLiveData<Int>()
    val mCurrentWord = MutableLiveData<BaseWordEntity>()

    suspend fun loadData0(table: String, page: Int, pageSize: Int) {
        withContext(Dispatchers.IO) {
            pagedWordCEt4.addAll(repository.getWordCEt4ByPage(table, page, pageSize))
        }
        mCurrentWordIndex.value = 0
        mCurrentWord.value = pagedWordCEt4[mCurrentWordIndex.value!!]
    }

}