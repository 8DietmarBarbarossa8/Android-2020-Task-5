package com.bignerdranch.android.thecatapi.api

import com.bignerdranch.android.thecatapi.utils.Utils
import com.bignerdranch.android.thecatapi.models.CatResult
import retrofit2.http.GET

interface Api {
    @GET(Utils.QUERY_KEY)
    suspend fun getListOfCats(): List<CatResult>
}