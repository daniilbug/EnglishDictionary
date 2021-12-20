package com.github.daniilbug.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SearchEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
}