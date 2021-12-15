package com.github.daniilbug.core.presentation.definition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DefinitionsViewModel @AssistedInject constructor(
    @Assisted private val word: String
) : ViewModel() {

    @AssistedFactory
    interface Creator {
        fun create(word: String): DefinitionsViewModel
    }

    private val mutableState = MutableStateFlow<DefinitionsState>(DefinitionsState.Loading)
    val state = mutableState.asStateFlow()

    init {
        setupDefinitions()
    }

    private fun setupDefinitions() = viewModelScope.launch {
        delay(500L)
        mutableState.value = DefinitionsState.Definitions(
            word,
            "https://images.unsplash.com/photo-1507146426996-ef05306b995a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8cHVwcHklMjBkb2d8ZW58MHx8MHx8&w=1000&q=80",
            List(10) { index ->
                DefinitionUI("Part$index", "Definition".repeat(index + 1))
            }
        )
    }
}