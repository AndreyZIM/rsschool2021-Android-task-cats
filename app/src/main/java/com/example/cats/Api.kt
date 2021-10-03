package com.example.cats

import com.example.cats.model.ApiData
import com.example.cats.model.Cat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.moshi.MoshiConverterFactory

interface CatsApi {

    @GET("search?limit=10")
    suspend fun getListOfCats(): List<ApiData>
}

object CatsApiImpl {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://api.thecatapi.com/v1/images/")
        .build()

    private val CatsService = retrofit.create(CatsApi::class.java)

    suspend fun getListOfCats(): List<Cat> {
        return withContext(Dispatchers.IO) {
            CatsService.getListOfCats().map {
                url -> Cat(url.imageUrl)
            }
        }
    }
}