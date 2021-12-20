package com.github.daniilbug.core.data.rest.image

interface ImagesError {
    class ConnectionError(val throwable: Throwable) : ImagesError
    class UnexpectedError(val throwable: Throwable) : ImagesError
}