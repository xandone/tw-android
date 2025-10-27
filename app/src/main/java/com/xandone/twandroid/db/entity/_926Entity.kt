package com.xandone.twandroid.db.entity

import androidx.room.Entity
import androidx.room.Index
import com.xandone.twandroid.db.DBInfo

/**
 * @author: xiao
 * created on: 2025/10/22 16:15
 * description:
 */
@Entity(
    tableName = DBInfo.TABLE_926,
    indices = [
        Index(name = "idx__926_id", value = ["id"]),
        Index(name = "idx__926_word", value = ["word"])
    ]
)
class _926Entity : BaseWordEntity()