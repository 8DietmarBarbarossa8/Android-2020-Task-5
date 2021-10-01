package com.bignerdranch.android.thecatapi.api

import com.bignerdranch.android.thecatapi.Keys
import com.bignerdranch.android.thecatapi.models.CatResult
import retrofit2.http.GET

interface Api {
    @GET(Keys.QUERY)
    suspend fun getListOfCats(): List<CatResult>
}