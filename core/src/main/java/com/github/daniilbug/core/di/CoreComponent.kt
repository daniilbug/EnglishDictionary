package com.github.daniilbug.core.di

import android.content.Context
import com.github.daniilbug.core.core.DaggerViewModelFactory
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ViewModelsModule::class])
interface CoreComponent {
    fun getViewModelFactory(): DaggerViewModelFactory

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun build(): CoreComponent
    }
}