package com.github.daniilbug.core.presentation.search

data class SearchState(val searchQuery: String) {
    companion object {
        fun empty() = SearchState("")
    }
}