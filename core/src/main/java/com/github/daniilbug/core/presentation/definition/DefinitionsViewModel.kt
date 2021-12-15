package com.github.daniilbug.core.presentation.definition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.daniilbug.core.navigation.AppRouter
import com.github.daniilbug.core.navigation.AppScreen
import com.github.daniilbug.core.navigation.Command
import com.github.daniilbug.core.navigation.invoke
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DefinitionsViewModel @AssistedInject constructor(
    @Assisted private val word: String,
    private val router: AppRouter
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

    fun sendEvent(event: DefinitionsEvent) {
        when (event) {
            is DefinitionsEvent.OpenImage -> router(
                Command.Open(AppScreen.Image(event.imageUrl))
            )
        }
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