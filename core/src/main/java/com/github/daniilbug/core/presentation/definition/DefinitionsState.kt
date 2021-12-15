package com.github.daniilbug.core.presentation.definition

sealed class DefinitionsState {
    object Loading : DefinitionsState()
    data class Error(val message: String) : DefinitionsState()
    data class Definitions(
        val word: String,
        val imageUrl: String,
        val definitions: List<DefinitionUI>
    ) : DefinitionsState()
}