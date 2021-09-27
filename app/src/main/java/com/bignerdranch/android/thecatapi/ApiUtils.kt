package com.bignerdranch.android.thecatapi

import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.converter.moshi.MoshiConverterFactory

interface Api {
    @GET("/v1/images/search?limit=10&page=1&order=Desc")
    suspend fun getCats(
        @Query("limit") size: Int,
        @Query("page") page: Int
    ) : List<Cat>
}

object ApiDateImplementation {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://api.thecatapi.com")
        .build()
    val catService: Api = retrofit.create(Api::class.java)
}