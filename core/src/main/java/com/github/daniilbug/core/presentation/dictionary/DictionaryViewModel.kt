package com.github.daniilbug.core.presentation.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.daniilbug.core.navigation.AppRouter
import com.github.daniilbug.core.navigation.AppScreen
import com.github.daniilbug.core.navigation.Command
import com.github.daniilbug.core.navigation.invoke
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class DictionaryViewModel @Inject constructor(
    private val router: AppRouter
) : ViewModel() {

    private val mutableState = MutableStateFlow<DictionaryState>(DictionaryState.Loading)
    val state = mutableState.asStateFlow()

    init {
        setupDictionary()
    }

    fun sendEvent(event: DictionaryEvent) {
        when (event) {
            DictionaryEvent.OpenSearch -> router(Command.Open(AppScreen.Search))
        }
    }

    private fun setupDictionary() = viewModelScope.launch {
        delay(500L)
        mutableState.value = DictionaryState.DictionaryItems(
            items = List(10) { index ->
                DictionaryItemUI("Word $index", "Definition".repeat(index))
            }
        )
    }
}