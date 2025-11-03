package com.xandone.twandroid.bean

/**
 * @author: xiao
 * created on: 2025/10/31 14:07
 * description:
 */

data class ErrorWordDetailsBean(
    val wid: Int = 0,
    val errortable: String?,
    val errorwid: Int?,
    val errorid: Int?,
    val word: String?,
    val errorcount: Int,
    //详情
    val phonetic0: String? = null,
    val trans: String? = null,
    val sentences: String? = null
)