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
    tableName = DBInfo.TABLE_DUOLINGO_VOCABULARY_B2,
    indices = [
        Index(name = "idx_duolingo_vocabulary_b2_id", value = ["id"]),
        Index(name = "idx_duolingo_vocabulary_b2_word", value = ["word"])
    ]
)
class DuolinguoB2 : BaseWordEntity()