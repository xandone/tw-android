package com.xandone.twandroid.db.entity

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
    tableName = DBInfo.TABLE_WORD_HOME,
)
class WordHomeEntity(
    @PrimaryKey(autoGenerate = true)
    var wid: Int = 0,
    val name: String,
    val description: String,
    val length: String,
    var learnlen: Int = 0,
    val category: String,
    val tablename: String?
)