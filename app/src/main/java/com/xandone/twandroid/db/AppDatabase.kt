package com.xandone.twandroid.db

import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.xandone.twandroid.App
import com.xandone.twandroid.db.dao.DuolinguoB1Dao
import com.xandone.twandroid.db.dao.DuolinguoB2Dao
import com.xandone.twandroid.db.dao.ErrorWordDao
import com.xandone.twandroid.db.dao.KaoyanDao
import com.xandone.twandroid.db.dao.NceNew1Dao
import com.xandone.twandroid.db.dao.NceNew2Dao
import com.xandone.twandroid.db.dao.NceNew3Dao
import com.xandone.twandroid.db.dao.NceNew4Dao
import com.xandone.twandroid.db.dao.Oxford3000Dao
import com.xandone.twandroid.db.dao.VoaDao
import com.xandone.twandroid.db.dao.WordCEt4Dao
import com.xandone.twandroid.db.dao.WordCEt6Dao
import com.xandone.twandroid.db.dao.WordCoderDao
import com.xandone.twandroid.db.dao.WordHomeDao
import com.xandone.twandroid.db.dao.WordSecretDao
import com.xandone.twandroid.db.dao._926Dao
import com.xandone.twandroid.db.entity.DuolinguoB1
import com.xandone.twandroid.db.entity.DuolinguoB2
import com.xandone.twandroid.db.entity.ErrorWord
import com.xandone.twandroid.db.entity.Kaoyan
import com.xandone.twandroid.db.entity.NceNew1
import com.xandone.twandroid.db.entity.NceNew2
import com.xandone.twandroid.db.entity.NceNew3
import com.xandone.twandroid.db.entity.NceNew4
import com.xandone.twandroid.db.entity.Oxford3000
import com.xandone.twandroid.db.entity.Voa
import com.xandone.twandroid.db.entity.WordCEt4
import com.xandone.twandroid.db.entity.WordCEt6
import com.xandone.twandroid.db.entity.WordCoder
import com.xandone.twandroid.db.entity.WordHomeEntity
import com.xandone.twandroid.db.entity.WordSecret
import com.xandone.twandroid.db.entity._926Entity

/**
 * @author: xiao
 * created on: 2025/10/22 16:12
 * description:
 */

@Database(
    entities = [
        WordCEt4::class,
        WordCEt6::class,
        Kaoyan::class,
        _926Entity::class,
        WordSecret::class,

        DuolinguoB1::class,
        DuolinguoB2::class,
        Oxford3000::class,
        Voa::class,

        NceNew1::class,
        NceNew2::class,
        NceNew3::class,
        NceNew4::class,

        WordCoder::class,

        ErrorWord::class,
        WordHomeEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordCEt4Dao(): WordCEt4Dao
    abstract fun wordCEt6Dao(): WordCEt6Dao
    abstract fun kaoyanDao(): KaoyanDao
    abstract fun _926Dao(): _926Dao
    abstract fun wordSecretDao(): WordSecretDao

    abstract fun duolinguoB1Dao(): DuolinguoB1Dao
    abstract fun duolinguoB2Dao(): DuolinguoB2Dao
    abstract fun oxford3000Dao(): Oxford3000Dao
    abstract fun voaDao(): VoaDao

    abstract fun nceNew1Dao(): NceNew1Dao
    abstract fun nceNew2Dao(): NceNew2Dao
    abstract fun nceNew3Dao(): NceNew3Dao
    abstract fun nceNew4Dao(): NceNew4Dao

    abstract fun wordCoderDao(): WordCoderDao

    abstract fun errorWordDao(): ErrorWordDao
    abstract fun wordHomeDao(): WordHomeDao

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

