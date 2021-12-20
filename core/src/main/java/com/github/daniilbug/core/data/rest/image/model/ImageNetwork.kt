package com.github.daniilbug.core.data.rest.image.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ImageNetwork(
    @Json(name = "id")
    val id: Int,
    @Json(name = "imageurl")
    val imageUrl: String
)