package com.github.daniilbug.core.domain.model

data class DictionaryAnswerDomain(
    val imageUrl: String?,
    val definitions: List<DefinitionDomain>
)