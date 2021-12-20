package com.github.daniilbug.core.data.rest

import com.github.daniilbug.core.core.result.BinaryResult
import com.github.daniilbug.core.data.rest.model.DictionaryAnswerNetwork
import retrofit2.http.GET

interface DictionaryApi {

    companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/"
    }

    @GET("entries/en/cat")
    suspend fun getDefinitions(): BinaryResult<DictionaryError, List<DictionaryAnswerNetwork>>
}