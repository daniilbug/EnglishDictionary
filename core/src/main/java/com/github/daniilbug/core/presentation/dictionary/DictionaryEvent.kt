package com.github.daniilbug.core.presentation.dictionary

sealed class DictionaryEvent {
    object OpenSearch: DictionaryEvent()
    class Search(val word: String): DictionaryEvent()
}