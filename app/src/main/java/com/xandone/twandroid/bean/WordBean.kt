package com.xandone.twandroid.bean

import com.xandone.twandroid.db.entity.BaseWordEntity

/**
 * @author: xiao
 * created on: 2025/10/30 10:13
 * description:
 */
data class WordBean(var errorWord: String? = null, var keyword: String? = null) : BaseWordEntity()
