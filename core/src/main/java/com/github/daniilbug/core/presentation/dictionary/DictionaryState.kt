package com.github.daniilbug.core.presentation.dictionary

sealed class DictionaryState {
    object Loading : DictionaryState()

    data class DictionaryItems(
        val items: List<DictionaryItemUI>
    ) : DictionaryState()

    data class Error(
        val message: String
    ) : DictionaryState()
}