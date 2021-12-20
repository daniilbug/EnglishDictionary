package com.github.daniilbug.core.di.dictionary

import com.github.daniilbug.core.domain.repo.DictionaryRepository
import com.github.daniilbug.core.domain.source.DictionaryRemoteDataSource
import com.github.daniilbug.core.domain.source.SearchHistoryLocalDataSource
import com.github.daniilbug.core.domain.source.impl.RestDictionaryRemoteDataSource
import com.github.daniilbug.core.domain.source.impl.RoomSearchHistoryLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        DictionaryNetworkModule::class,
        ImagesNetworkModule::class,
        SearchHistoryDatabaseModule::class
    ]
)
interface DictionaryModule {

    @Module
    companion object {

        @Singleton
        @Provides
        fun provideDictionaryRepository(
            dictionaryRemoteDataSource: DictionaryRemoteDataSource,
            historyLocalDataSource: SearchHistoryLocalDataSource
        ): DictionaryRepository = DictionaryRepository(
            dictionaryRemoteDataSource,
            historyLocalDataSource
        )
    }

    @Singleton
    @Binds
    fun bindDictionaryRemoteDataSource(
        dataSource: RestDictionaryRemoteDataSource
    ): DictionaryRemoteDataSource

    @Singleton
    @Binds
    fun bindHistoryLocalDataSource(
        dataSource: RoomSearchHistoryLocalDataSource
    ): SearchHistoryLocalDataSource
}