package com.github.daniilbug.core.navigation

sealed interface AppScreen {
    object DictionaryList: AppScreen
    object Search: AppScreen
    class Definition(val word: String): AppScreen
    class Image(val imageUrl: String): AppScreen
}