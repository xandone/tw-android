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
    tableName = DBInfo.TABLE_NCE_NEW_2,
    indices = [
        Index(name = "idx_nce_new_2_id", value = ["id"]),
        Index(name = "idx_nce_new_2_word", value = ["word"])
    ]
)
class NceNew2 : BaseWordEntity()