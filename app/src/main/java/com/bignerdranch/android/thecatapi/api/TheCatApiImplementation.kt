package com.bignerdranch.android.thecatapi.api

import com.bignerdranch.android.thecatapi.utils.Utils
import com.bignerdranch.android.thecatapi.models.Cat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TheCatApiImplementation {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Utils.CAT_API_SITE_KEY)
        .build()

    private val theCatRetrofit = retrofit.create(Api::class.java)

    suspend fun getListOfCats(): MutableList<Cat> {
        return try {
            withContext(Dispatchers.IO) {
                theCatRetrofit.getListOfCats()
                    .map { result ->
                        Cat(
                            result.id,
                            result.url
                        )
                    }.toMutableList()
            }
        } catch (e: Exception) {
            mutableListOf()
        }
    }
}