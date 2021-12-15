package com.github.daniilbug.core.presentation.definition

sealed class DefinitionsEvent {
    class OpenImage(val imageUrl: String): DefinitionsEvent()
}