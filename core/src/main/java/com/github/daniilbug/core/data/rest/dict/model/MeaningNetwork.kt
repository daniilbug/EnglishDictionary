package com.github.daniilbug.core.data.rest.dict.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MeaningNetwork(
    @Json(name = "partOfSpeech")
    val partOfSpeech: String = "",
    @Json(name = "definitions")
    val definitions: List<DefinitionNetwork>
)