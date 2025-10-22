package com.xandone.twandroid.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.xandone.twandroid.WordRepository
import com.xandone.twandroid.db.entity.WordCEt4
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * @author: xiao
 * created on: 2025/10/22 16:35
 * description:
 */
class CEt4ViewModel(repository: WordRepository) : ViewModel() {
    val pagedWordCEt4: Flow<PagingData<WordCEt4>> = repository.getPagedWordCEt4()
        .cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
        }
    }
}