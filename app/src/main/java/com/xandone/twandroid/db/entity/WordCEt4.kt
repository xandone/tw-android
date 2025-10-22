package com.xandone.twandroid.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author: xiao
 * created on: 2025/10/22 16:15
 * description:
 */
@Entity(tableName = "cet4_words")
class WordCEt4(
    @PrimaryKey(autoGenerate = true) val wid: Int = 0,
    val id: Int,
    val word: String
)