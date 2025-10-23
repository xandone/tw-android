package com.xandone.twandroid.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xandone.twandroid.WordRepository
import com.xandone.twandroid.db.entity.WordCEt4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: xiao
 * created on: 2025/10/22 16:35
 * description:
 */
class CEt4ViewModel(private val repository: WordRepository) : ViewModel() {
    val pagedWordCEt4 = mutableListOf<WordCEt4>()

    private val _currentIndex = MutableLiveData<Int>()
    val mCurrentWordIndex = _currentIndex

    private val _currentWord = MutableLiveData<WordCEt4>()
    val mCurrentWord = _currentWord

    suspend fun loadData0(page: Int, pageSize: Int) {
        _currentIndex.value = 0
        withContext(Dispatchers.IO) {
            pagedWordCEt4.addAll(repository.getWordCEt4ByPage(page, pageSize))
        }
        mCurrentWord.value = pagedWordCEt4[_currentIndex.value!!]
    }

    fun changeWord() {
        if (_currentIndex.value!! < pagedWordCEt4.size) {
            _currentIndex.value = _currentIndex.value!! + 1
            mCurrentWord.value = pagedWordCEt4[_currentIndex.value!!]
            Log.d("sfsdfsdfsd", "changeWord: ${mCurrentWord.value?.word}")
        }
    }
}