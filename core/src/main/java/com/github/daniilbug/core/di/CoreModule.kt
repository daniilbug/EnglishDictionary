package com.github.daniilbug.core.di

import com.github.daniilbug.core.di.database.DatabaseModule
import com.github.daniilbug.core.di.dictionary.DictionaryModule
import com.github.daniilbug.core.di.dictionary.SearchHistoryDatabaseModule
import com.github.daniilbug.core.di.network.NetworkModule
import dagger.Module

@Module(
    includes = [
        NavigationModule::class,
        ViewModelsModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        DictionaryModule::class,
    ]
)
object CoreModule