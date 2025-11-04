package com.xandone.twandroid.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.xandone.twandroid.db.DBInfo

/**
 * @author: xiao
 * created on: 2025/10/23 14:58
 * description:
 */
@Entity(tableName = DBInfo.TABLE_ERROR_WORD)
class ErrorWord(
    @PrimaryKey(autoGenerate = true)
    val wid: Int = 0,
    //表名称
    val errortable: String?,
    val errorwid: Int?,
    val errorid: Int?,
    val word: String?,
    val phonetic0: String? = null,
    val trans: String? = null,
    val sentences: String? = null,
    var errorcount: Int
) {
    @Transient
    var isSelect: Boolean = false
}

