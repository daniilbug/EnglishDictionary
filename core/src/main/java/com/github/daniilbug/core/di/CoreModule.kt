package com.github.daniilbug.core.di

import com.github.daniilbug.core.core.string.ResourcesStringResolver
import com.github.daniilbug.core.core.string.StringResolver
import com.github.daniilbug.core.di.database.DatabaseModule
import com.github.daniilbug.core.di.dictionary.DictionaryModule
import com.github.daniilbug.core.di.dictionary.SearchHistoryDatabaseModule
import com.github.daniilbug.core.di.network.NetworkModule
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(
    includes = [
        NavigationModule::class,
        ViewModelsModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        DictionaryModule::class,
    ]
)
interface CoreModule {

    @Binds
    @Singleton
    fun bindStringResolver(stringResolver: ResourcesStringResolver): StringResolver
}