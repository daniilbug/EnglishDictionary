package com.github.daniilbug.core.data.rest.dict.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DictionaryAnswerNetwork(
    @Json(name = "word")
    val word: String,
    @Json(name = "meanings")
    val meanings: List<MeaningNetwork>
)