package com.github.daniilbug.core.navigation

interface AppRouter {
    fun command(command: Command)
}

operator fun AppRouter.invoke(command: Command) {
    command(command)
}