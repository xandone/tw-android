package com.xandone.twandroid

import android.app.Application
import android.util.Log
import com.xandone.twandroid.utils.DatabaseImporter

/**
 * @author: xiao
 * created on: 2025/10/22 15:50
 * description:
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initDb()
    }

    fun initDb() {
        val dbName = "word.db"
        val isSuccess = DatabaseImporter.importDatabase(this, dbName)
        if (isSuccess) {
            val version = DatabaseImporter.getDatabaseVersion(this, dbName)
        } else {
        }
    }
}