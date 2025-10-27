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
    tableName = DBInfo.TABLE_OXFORD3000,
    indices = [
        Index(name = "idx_oxford3000_id", value = ["id"]),
        Index(name = "idx_oxford3000_word", value = ["word"])
    ]
)
class Oxford3000 : BaseWordEntity()