package com.github.daniilbug.core.presentation.search

sealed class SearchEvent {
    class UpdateQuery(val newQuery: String): SearchEvent()
    object Search: SearchEvent()
    object Back: SearchEvent()
}