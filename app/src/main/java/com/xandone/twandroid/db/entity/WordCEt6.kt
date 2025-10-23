package com.xandone.twandroid.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @author: xiao
 * created on: 2025/10/22 16:15
 * description:
 */
@Entity(
    tableName = "cet6_words", indices = [
        Index(name = "idx_cet6_id", value = ["id"]),
        Index(name = "idx_cet6_word", value = ["word"])
    ]
)
class WordCEt6(
    @PrimaryKey(autoGenerate = true) val wid: Int?,
    val id: Int?,
    val word: String?,
    val phonetic0: String?,
    val phonetic1: String?,
    val trans: String?,
    val sentences: String?,
    val phrases: String?,
    val synos: String?,
    val relWords: String?,
    val etymology: String?,
)