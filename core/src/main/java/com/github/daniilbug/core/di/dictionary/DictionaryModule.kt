package com.github.daniilbug.core.di.dictionary

import com.github.daniilbug.core.domain.repo.DictionaryRepository
import com.github.daniilbug.core.domain.source.DictionaryRemoteDataSource
import com.github.daniilbug.core.domain.source.impl.RestDictionaryRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DictionaryNetworkModule::class])
interface DictionaryModule {

    @Module
    companion object {

        @Singleton
        @Provides
        fun provideDictionaryRepository(
            remoteDataSource: DictionaryRemoteDataSource
        ): DictionaryRepository = DictionaryRepository(remoteDataSource)
    }

    @Singleton
    @Binds
    fun bindRemoteDataSource(dataSource: RestDictionaryRemoteDataSource): DictionaryRemoteDataSource
}