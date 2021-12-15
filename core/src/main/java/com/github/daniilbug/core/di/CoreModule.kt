package com.github.daniilbug.core.di

import dagger.Module

@Module(includes = [NavigationModule::class, ViewModelsModule::class])
object CoreModule