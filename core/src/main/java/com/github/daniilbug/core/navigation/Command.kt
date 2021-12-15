package com.github.daniilbug.core.navigation

sealed interface Command {
    class Replace(val screen: AppScreen): Command
    class Open(val screen: AppScreen): Command
    object Back: Command
}