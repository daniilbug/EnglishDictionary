package com.github.daniilbug.dict.di

import android.content.Context
import com.github.daniilbug.core.core.DaggerViewModelFactory
import com.github.daniilbug.core.di.CoreModule
import com.github.daniilbug.core.navigation.FlowAppRouter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class])
interface WatchAppComponent {
    fun getViewModelFactory(): DaggerViewModelFactory
    fun getFlowRouter(): FlowAppRouter

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun build(): WatchAppComponent
    }
}