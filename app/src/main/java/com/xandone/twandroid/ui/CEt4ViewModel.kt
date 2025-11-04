package com.xandone.twandroid.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.xandone.twandroid.bean.WordBean
import com.xandone.twandroid.config.Constants
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
    val pagedWordCEt4 = mutableListOf<WordBean>()

    val mCurrentWordIndex = MutableLiveData<Int>()
    val mCurrentWord = MutableLiveData<WordBean>()

    var tablename: String? = null

    var dailyCount: Int = SPUtils.getInstance().getInt(Constants.SP_DAILY, 20)

    suspend fun loadData0(table: String, page: Int, pageSize: Int) {
        withContext(Dispatchers.IO) {
            val datas = repository.loadDB(table, page, pageSize).map { base ->
                WordBean().also { w ->
                    w.wid = base.wid
                    w.id = base.id
                    w.word = base.word
                    w.phonetic0 = base.phonetic0
                    w.phonetic1 = base.phonetic1
                    w.trans = base.trans
                    w.sentences = base.sentences
                    w.phrases = base.phrases
                    w.synos = base.synos
                    w.relWords = base.relWords
                    w.etymology = base.etymology
                }
            }
            pagedWordCEt4.clear()
            pagedWordCEt4.addAll(datas)
        }
        mCurrentWordIndex.value = 0
        mCurrentWord.value = pagedWordCEt4[mCurrentWordIndex.value!!]
    }

}