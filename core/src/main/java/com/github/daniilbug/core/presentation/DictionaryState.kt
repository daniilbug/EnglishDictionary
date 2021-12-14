package com.github.daniilbug.core.presentation

sealed class DictionaryState {
    object Loading : DictionaryState()

    data class DictionaryItems(
        val items: List<DictionaryItemUI>
    ) : DictionaryState()

    data class Error(
        val message: String
    ) : DictionaryState()
}