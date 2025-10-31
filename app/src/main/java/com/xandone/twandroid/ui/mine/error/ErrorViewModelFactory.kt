package com.xandone.twandroid.ui.mine.error

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.xandone.twandroid.repository.ErrorRepository

/**
 * @author: xiao
 * created on: 2025/10/31 13:44
 * description:
 */
class ErrorViewModelFactory (private val repository: ErrorRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ErrorViewModel::class.java)) {
            return ErrorViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}