package com.github.daniilbug.core.data.rest

sealed interface DictionaryError {
    object NotFound: DictionaryError
    class ConnectionError(val throwable: Throwable) : DictionaryError
    class UnexpectedError(val throwable: Throwable) : DictionaryError
}
