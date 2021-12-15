package com.github.daniilbug.core.di

import androidx.lifecycle.ViewModel
import com.github.daniilbug.core.core.DaggerViewModelFactory
import com.github.daniilbug.core.core.ViewModelKey
import com.github.daniilbug.core.presentation.dictionary.DictionaryViewModel
import com.github.daniilbug.core.presentation.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import javax.inject.Singleton

@Module
interface ViewModelsModule {

    @Module
    companion object {

        @Provides
        @Singleton
        fun provideViewModelFactory(
            factoriesMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
        ): DaggerViewModelFactory {
            return DaggerViewModelFactory(factoriesMap)
        }
    }

    @Binds
    @ViewModelKey(DictionaryViewModel::class)
    @IntoMap
    fun bindDictionaryViewModel(viewModel: DictionaryViewModel): ViewModel

    @Binds
    @ViewModelKey(SearchViewModel::class)
    @IntoMap
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel
}