package com.github.daniilbug.core.presentation.definition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.daniilbug.core.core.result.BinaryResult
import com.github.daniilbug.core.data.rest.dict.DictionaryError
import com.github.daniilbug.core.domain.model.DefinitionDomain
import com.github.daniilbug.core.domain.model.DictionaryAnswerDomain
import com.github.daniilbug.core.domain.repo.DictionaryRepository
import com.github.daniilbug.core.navigation.AppRouter
import com.github.daniilbug.core.navigation.AppScreen
import com.github.daniilbug.core.navigation.Command
import com.github.daniilbug.core.navigation.invoke
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn

class DefinitionsViewModel @AssistedInject constructor(
    @Assisted private val word: String,
    private val router: AppRouter,
    dictionaryRepository: DictionaryRepository
) : ViewModel() {

    @AssistedFactory
    interface Creator {
        fun create(word: String): DefinitionsViewModel
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = dictionaryRepository.getDefinitions(word)
        .mapLatest { result ->
            when(result) {
                is BinaryResult.Error -> createErrorState(result.error)
                is BinaryResult.Success -> createSuccessState(result.data)
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, DefinitionsState.Loading)

    fun sendEvent(event: DefinitionsEvent) {
        when (event) {
            is DefinitionsEvent.OpenImage -> router(
                Command.Open(AppScreen.Image(event.imageUrl))
            )
        }
    }

    private fun createErrorState(error: DictionaryError): DefinitionsState {
        return when(error) {
            is DictionaryError.ConnectionError -> DefinitionsState.Error("Connection Error")
            DictionaryError.NotFound -> DefinitionsState.Error("Not found")
            is DictionaryError.UnexpectedError -> DefinitionsState.Error("Unexpected Error")
        }
    }

    private fun createSuccessState(answer: DictionaryAnswerDomain): DefinitionsState {
        return DefinitionsState.Definitions(
            word,
            answer.imageUrl,
            answer.definitions.map(DefinitionDomain::asUI)
        )
    }
}