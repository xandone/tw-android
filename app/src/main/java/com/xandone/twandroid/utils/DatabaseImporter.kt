package com.xandone.twandroid.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.io.*

/**
 * @author: xiao
 * created on: 2025/10/22 15:48
 * description:
 */
object DatabaseImporter {

    fun importDatabase(context: Context, dbName: String): Boolean {
        val dbPath = context.getDatabasePath(dbName).path
        val dbDir = File(dbPath).parentFile

        if (File(dbPath).exists()) {
            return true
        }

        if (dbDir != null && !dbDir.exists()) {
            if (!dbDir.mkdirs()) {
                return false
            }
        }

        return try {
            val inputStream: InputStream = context.assets.open(dbName)
            val outputStream = FileOutputStream(dbPath)

            val buffer = ByteArray(1024 * 4)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()

            File(dbPath).exists()
        } catch (e: IOException) {
            e.printStackTrace()
            false // 复制失败
        }
    }

    /**
     * 获取数据库版本号
     */
    fun getDatabaseVersion(context: Context, dbName: String): Int {
        val dbPath = context.getDatabasePath(dbName).path
        return try {
            val db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY)
            val version = db.version
            db.close()
            version
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }
}