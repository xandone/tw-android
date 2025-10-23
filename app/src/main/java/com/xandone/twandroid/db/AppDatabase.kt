package com.xandone.twandroid.db

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.xandone.twandroid.db.dao.WordCEt4Dao
import com.xandone.twandroid.db.entity.WordCEt4
import com.xandone.twandroid.db.entity.WordCEt6

/**
 * @author: xiao
 * created on: 2025/10/22 16:12
 * description:
 */

@Database(entities = [WordCEt4::class, WordCEt6::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordCEt4Dao(): WordCEt4Dao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // 预填充数据库
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "word.db"
                ).createFromAsset("word.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

