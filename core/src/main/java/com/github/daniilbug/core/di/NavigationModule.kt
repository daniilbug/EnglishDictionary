package com.github.daniilbug.core.di

import com.github.daniilbug.core.navigation.AppRouter
import com.github.daniilbug.core.navigation.FlowAppRouter
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface NavigationModule {

    @Module
    companion object {

        @Provides
        @Singleton
        fun provideRouter(): FlowAppRouter = FlowAppRouter()
    }

    @Binds
    fun bindRouter(router: FlowAppRouter): AppRouter
}