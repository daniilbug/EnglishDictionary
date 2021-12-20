package com.github.daniilbug.core.core.result

sealed class BinaryResult<out Error, out Data> {
    data class Success<Data>(val data: Data) : BinaryResult<Nothing, Data>()
    data class Error<Error>(val error: Error) : BinaryResult<Error, Nothing>()
}