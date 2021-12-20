package com.github.daniilbug.core.data.rest.dict.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class DefinitionNetwork(
    @Json(name = "definition")
    val definition: String
)