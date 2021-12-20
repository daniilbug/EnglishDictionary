package com.github.daniilbug.core.data.rest.image

import com.github.daniilbug.core.core.result.BinaryResult
import com.github.daniilbug.core.data.rest.image.model.ImagesAnswerNetwork
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImagesApi {
    companion object {
        const val BASE_URL = "http://glyffix.com/api/"
    }

    @GET("image/")
    suspend fun getImagesByWord(
        @Query("word") word: String
    ): BinaryResult<ImagesError, ImagesAnswerNetwork>
}