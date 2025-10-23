package com.xandone.twandroid.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.reflect.TypeToken
import com.xandone.twandroid.App
import com.xandone.twandroid.bean.WordHomeBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author: xiao
 * created on: 2025/10/23 17:48
 * description:
 */
class HomeViewModel : ViewModel() {
    private val _list = mutableListOf<List<WordHomeBean>>()
    val list = _list

    fun loadData0() {
        viewModelScope.launch {
            try {
                val jsonString = readAssetFile("dict-list.json")
                // 解析二维数组
                val wordList: List<List<WordHomeBean>> = GsonUtils.fromJson(
                    jsonString,
                    object : TypeToken<List<List<WordHomeBean>>>() {}.type
                )
                _list.clear()
                _list.addAll(wordList)  // 使用 addAll 而不是 add
                Log.d("HomeViewModel", "list: ${_list.size}")
            } catch (e: Exception) {
                Log.e("HomeViewModel", "读取JSON文件失败", e)
            }
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