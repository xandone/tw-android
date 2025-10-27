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
    tableName = DBInfo.TABLE_KAOYAN_3_T,
    indices = [
        Index(name = "idx_kaoyan_3_t_id", value = ["id"]),
        Index(name = "idx_kaoyan_3_t_word", value = ["word"])
    ]
)
class Kaoyan : BaseWordEntity()