package com.github.daniilbug.core.presentation.definition

sealed class DefinitionsState {
    abstract val word: String

    data class Loading(override val word: String) : DefinitionsState()
    data class NotFound(override val word: String): DefinitionsState()
    data class Error(override val word: String, val message: String) : DefinitionsState()
    data class Definitions(
        override val word: String,
        val imageUrl: String?,
        val definitions: List<DefinitionUI>
    ) : DefinitionsState()
}