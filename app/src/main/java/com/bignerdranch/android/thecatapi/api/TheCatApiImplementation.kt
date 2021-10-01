package com.bignerdranch.android.thecatapi.api

import com.bignerdranch.android.thecatapi.model.Cat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TheCatApiImplementation {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.thecatapi.com")
        .build()

    private val theCatRetrofit = retrofit.create(Api::class.java)

    suspend fun getListOfCats(): List<Cat> {
        return try {
            withContext(Dispatchers.IO) {
                theCatRetrofit.getListOfCats()
                    .map { result ->
                        Cat(
                            result.id,
                            result.url
                        )
                    }
            }
        } catch (e: Exception) {
            listOf()
        }
    }
}