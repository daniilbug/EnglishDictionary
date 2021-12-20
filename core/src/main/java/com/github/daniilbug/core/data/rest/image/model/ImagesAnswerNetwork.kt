package com.github.daniilbug.core.data.rest.image.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ImagesAnswerNetwork(
    @Json(name = "success")
    val success: Boolean,
    @Json(name = "word")
    val word: String,
    @Json(name = "errormessage")
    val errorMessage: String,
    @Json(name = "data")
    val data: List<ImageNetwork>
)

