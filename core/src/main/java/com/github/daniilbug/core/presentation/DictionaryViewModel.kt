package com.github.daniilbug.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class DictionaryViewModel @Inject constructor() : ViewModel() {

    private val mutableState = MutableStateFlow<DictionaryState>(DictionaryState.Loading)
    val state = mutableState.asStateFlow()

    init {
        setupDictionary()
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