package com.xandone.twandroid.db

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.xandone.twandroid.App
import com.xandone.twandroid.db.dao.ErrorWordDao
import com.xandone.twandroid.db.dao.WordCEt4Dao
import com.xandone.twandroid.db.dao.WordCEt6Dao
import com.xandone.twandroid.db.entity.ErrorWord
import com.xandone.twandroid.db.entity.WordCEt4
import com.xandone.twandroid.db.entity.WordCEt6

/**
 * @author: xiao
 * created on: 2025/10/22 16:12
 * description:
 */

@Database(
    entities = [WordCEt4::class, WordCEt6::class, ErrorWord::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordCEt4Dao(): WordCEt4Dao
    abstract fun wordCEt6Dao(): WordCEt6Dao
    abstract fun errorWordDao(): ErrorWordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // 预填充数据库
                val instance = Room.databaseBuilder(
                    App.instance,
                    AppDatabase::class.java,
                    DBInfo.DB_NAME
                ).createFromAsset(DBInfo.DB_NAME)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

