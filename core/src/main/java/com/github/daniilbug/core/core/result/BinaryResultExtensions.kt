package com.github.daniilbug.core.core.result

fun <ErrorIn, DataIn, ErrorOut, DataOut> BinaryResult<ErrorIn, DataIn>.map(
    onData: (data: DataIn) -> DataOut,
    onError: (error: ErrorIn) -> ErrorOut
): BinaryResult<ErrorOut, DataOut> {
    return when (this) {
        is BinaryResult.Success -> BinaryResult.Success(onData(data))
        is BinaryResult.Error -> BinaryResult.Error(onError(error))
    }
}

fun <Error, Data> BinaryResult<Error, Data>.orNull(): Data? {
    return when (this) {
        is BinaryResult.Success -> data
        is BinaryResult.Error -> null
    }
}

fun <Error, DataIn, DataOut> BinaryResult<Error, DataIn>.mapData(
    body: (data: DataIn) -> DataOut,
): BinaryResult<Error, DataOut> {
    return when (this) {
        is BinaryResult.Success -> BinaryResult.Success(body(data))
        is BinaryResult.Error -> this
    }
}

fun <Data, ErrorIn, ErrorOut> BinaryResult<ErrorIn, Data>.mapError(
    body: (error: ErrorIn) -> ErrorOut,
): BinaryResult<ErrorOut, Data> {
    return when (this) {
        is BinaryResult.Success -> this
        is BinaryResult.Error -> BinaryResult.Error(body(error))
    }
}

fun <Error, Data> BinaryResult<Error, Data>.noData(): BinaryResult<Error, Unit> = mapData { }
