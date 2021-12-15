package com.github.daniilbug.core.navigation

sealed interface AppScreen {
    object DictionaryList: AppScreen
    object Search: AppScreen
}