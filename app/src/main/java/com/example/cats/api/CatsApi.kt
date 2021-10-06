package com.example.cats.api

import com.example.cats.model.Cat
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CatsApi {

    companion object {
        const val BASE_URL = "https://api.thecatapi.com/v1/"
    }

    @Headers("x-api-key: 9ca24ee6-b70f-4fea-a4ee-769462ef45cf")
    @GET("images/search")
    suspend fun getListOfCats(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): List<Cat>
}