package com.xandone.twandroid.ui.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xandone.twandroid.repository.WordRepository
import com.xandone.twandroid.ui.CEt4ViewModel

/**
 * @author: xiao
 * created on: 2025/10/24 14:22
 * description:
 */
class CEt4ViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CEt4ViewModel::class.java)) {
            return CEt4ViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}