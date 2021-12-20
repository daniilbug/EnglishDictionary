package com.github.daniilbug.core.di.dictionary

import com.github.daniilbug.core.data.local.AppDatabase
import com.github.daniilbug.core.data.local.SearchHistoryDao
import com.github.daniilbug.core.domain.source.SearchHistoryLocalDataSource
import com.github.daniilbug.core.domain.source.impl.RoomSearchHistoryLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SearchHistoryDatabaseModule {

    @Provides
    fun provideDao(
        database: AppDatabase
    ): SearchHistoryDao = database.searchHistoryDao()
}