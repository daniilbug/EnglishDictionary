package com.github.daniilbug.core.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FlowAppRouter : AppRouter {
    private val mutableCommandFlow = MutableSharedFlow<Command>(
        extraBufferCapacity = 1
    )
    val commandFlow = mutableCommandFlow.asSharedFlow()

    override fun command(command: Command) {
        mutableCommandFlow.tryEmit(command)
    }
}