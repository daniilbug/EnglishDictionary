package com.github.daniilbug.dict.di

import com.github.daniilbug.core.core.DaggerViewModelFactory
import com.github.daniilbug.core.di.CoreComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(dependencies = [CoreComponent::class])
interface WatchAppComponent {
    fun getViewModelFactory(): DaggerViewModelFactory
}