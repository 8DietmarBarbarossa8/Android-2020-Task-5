package com.bignerdranch.android.thecatapi.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatResult(
    @Json(name = "description") val description: String?,
    @Json(name = "url") val url: String?
)
