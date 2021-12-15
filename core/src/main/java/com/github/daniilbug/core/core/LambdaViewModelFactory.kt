package com.github.daniilbug.core.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LambdaViewModelFactory(
    private val create: () -> ViewModel
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return create() as T
    }
}