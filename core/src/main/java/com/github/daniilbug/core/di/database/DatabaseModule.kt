package com.github.daniilbug.core.di.database

import android.content.Context
import androidx.room.Room
import com.github.daniilbug.core.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        context: Context
    ) = Room.databaseBuilder(
        context, AppDatabase::class.java, "dict.db"
    ).build()
}