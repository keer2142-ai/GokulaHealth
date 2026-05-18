package com.gokula.health

import android.app.Application
import com.gokula.health.data.AppDatabase
import com.gokula.health.data.GokulaRepository

class GokulaHealthApp : Application() {
    lateinit var repository: GokulaRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val database = AppDatabase.getInstance(this)
        repository = GokulaRepository(database.gokulaDao())
    }
}
