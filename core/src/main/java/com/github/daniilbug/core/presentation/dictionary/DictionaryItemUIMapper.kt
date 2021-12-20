package com.github.daniilbug.core.presentation.dictionary

import com.github.daniilbug.core.domain.model.SearchItemDomain

fun SearchItemDomain.asUI(): DictionaryItemUI {
    return DictionaryItemUI(word, firstDefinition)
}