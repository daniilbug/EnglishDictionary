package com.github.daniilbug.core.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.daniilbug.core.navigation.AppRouter
import com.github.daniilbug.core.navigation.AppScreen
import com.github.daniilbug.core.navigation.Command
import com.github.daniilbug.core.navigation.invoke
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val router: AppRouter
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")
    val state = searchQuery.map { query -> SearchState(query) }
        .stateIn(viewModelScope, SharingStarted.Lazily, SearchState.empty())

    fun sendEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateQuery -> searchQuery.value = event.newQuery
            SearchEvent.Search -> router(
                Command.Replace(AppScreen.Definition(word = searchQuery.value))
            )
        }
    }
}