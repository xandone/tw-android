package com.xandone.twandroid.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.reflect.TypeToken
import com.xandone.twandroid.App
import com.xandone.twandroid.db.entity.WordHomeEntity
import com.xandone.twandroid.repository.HomeRespository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author: xiao
 * created on: 2025/10/23 17:48
 * description:
 */
class HomeViewModel : ViewModel() {
    private val _list = mutableListOf<List<WordHomeEntity>>()
    lateinit var oneDArray: List<WordHomeEntity>

    suspend fun loadData0() {
        try {
            val homeRespository = HomeRespository()
            if (homeRespository.hasData()) {
                oneDArray = homeRespository.loadHomeData()
                return
            }
            val jsonString = readAssetFile("dict-list.json")
            val wordList: List<List<WordHomeEntity>> = GsonUtils.fromJson(
                jsonString,
                object : TypeToken<List<List<WordHomeEntity>>>() {}.type
            )
            _list.clear()
            _list.addAll(wordList)
            oneDArray = _list.flatten()
            homeRespository.insertHomeWordList(oneDArray)
        } catch (e: Exception) {
            Log.e("HomeViewModel", "读取JSON文件失败", e)
        }
    }

    private suspend fun readAssetFile(fileName: String): String {
        return withContext(Dispatchers.IO) {
            App.instance.assets.open(fileName).use { inputStream ->
                inputStream.bufferedReader().use { reader ->
                    reader.readText()
                }
            }
        }
    }
}