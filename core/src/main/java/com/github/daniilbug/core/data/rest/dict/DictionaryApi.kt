package com.github.daniilbug.core.data.rest.dict

import com.github.daniilbug.core.core.result.BinaryResult
import com.github.daniilbug.core.data.rest.dict.model.DictionaryAnswerNetwork
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/"
    }

    @GET("entries/en/{word}")
    suspend fun getDefinitions(
        @Path("word") word: String,
    ): BinaryResult<DictionaryError, List<DictionaryAnswerNetwork>>
}