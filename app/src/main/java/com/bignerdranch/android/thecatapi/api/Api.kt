package com.bignerdranch.android.thecatapi.api

import com.bignerdranch.android.thecatapi.model.CatResult
import retrofit2.http.GET

interface Api {
    @GET("/v1/images/search?limit=10&order=ACSapi_key=2de238b9-a088-43b0-8b22-f7625d821b36")
    suspend fun getListOfCats(): List<CatResult>
}