package com.xandone.twandroid.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.xandone.twandroid.db.DBInfo

@Entity(tableName = DBInfo.TABLE_PRACTICE_WOED)
data class PracticeWord(
    @PrimaryKey(autoGenerate = true)
    val wid: Int = 0,
    val practicetable: String?,
    val practicewid: Int?,
    val practiceid: Int?,
    val word: String?,
    var practicecount: Int
)
