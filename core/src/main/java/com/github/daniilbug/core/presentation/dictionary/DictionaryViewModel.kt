package com.github.daniilbug.core.presentation.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.daniilbug.core.domain.model.SearchItemDomain
import com.github.daniilbug.core.domain.repo.DictionaryRepository
import com.github.daniilbug.core.navigation.AppRouter
import com.github.daniilbug.core.navigation.AppScreen
import com.github.daniilbug.core.navigation.Command
import com.github.daniilbug.core.navigation.invoke
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class DictionaryViewModel @Inject constructor(
    private val router: AppRouter,
    dictionaryRepository: DictionaryRepository
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = dictionaryRepository.getSearchHistory()
        .mapLatest { history ->
            DictionaryState.DictionaryItems(history.map(SearchItemDomain::asUI))
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, DictionaryState.Loading)

    fun sendEvent(event: DictionaryEvent) {
        when (event) {
            DictionaryEvent.OpenSearch -> router(Command.Open(AppScreen.Search))
            is DictionaryEvent.Search -> router(Command.Open(AppScreen.Definition(event.word)))
        }
    }
}