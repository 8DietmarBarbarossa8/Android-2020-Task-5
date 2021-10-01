package com.bignerdranch.android.thecatapi.api

import com.bignerdranch.android.thecatapi.model.Cat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.Exception

object TheCatApiImplementation {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://api.thecatapi.com")
        .build()

    private val theCatRetrofit = retrofit.create(Api::class.java)

    suspend fun getListOfCats(): List<Cat> {
        return try {
            withContext(Dispatchers.IO) {
                theCatRetrofit.getListOfCats()
                    .map { result ->
                        Cat(
                            result.description,
                            result.url
                        )
                    }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}