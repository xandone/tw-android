package com.xandone.twandroid.db.entity

import androidx.room.PrimaryKey

/**
 * @author: xiao
 * created on: 2025/10/27 10:54
 * description:
 */
open class BaseWordEntity(
    @PrimaryKey(autoGenerate = true) var wid: Int? = 0,
    var id: Int? = 0,
    var word: String? = null,
    var phonetic0: String? = null,
    var phonetic1: String? = null,
    var trans: String? = null,
    var sentences: String? = null,
    var phrases: String? = null,
    var synos: String? = null,
    var relWords: String? = null,
    var etymology: String? = null,
)