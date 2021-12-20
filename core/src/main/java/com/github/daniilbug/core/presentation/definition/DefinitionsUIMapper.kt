package com.github.daniilbug.core.presentation.definition

import com.github.daniilbug.core.domain.model.DefinitionDomain

fun DefinitionDomain.asUI(): DefinitionUI {
    return DefinitionUI(partOfSpeech, definition)
}