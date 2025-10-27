package com.xandone.twandroid.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.xandone.twandroid.db.DBInfo

/**
 * @author: xiao
 * created on: 2025/10/22 16:15
 * description:
 */
@Entity(
    tableName = DBInfo.TABLE_CET6,
    indices = [
        Index(name = "idx_cet6_words_id", value = ["id"]),
        Index(name = "idx_cet6_words_word", value = ["word"])
    ]
)
class WordCEt6 : BaseWordEntity()