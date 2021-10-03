package com.example.cats.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
//data class ApiData(
////    @Json(name = "url") val imageUrl: String?
//    @Json(name = "status") val status: String,
//    @Json(name = "results") val results: List<Result>
//)
//
//@JsonClass(generateAdapter = true)
//data class Result(
//    @Json(name = "multimedia") val multimedia: Multimedia?
//)
//
//@JsonClass(generateAdapter = true)
//data class Multimedia(
//    @Json(name = "url") val imageUrl: String?
//)

@JsonClass(generateAdapter = true)
data class ApiData(
    @Json(name = "url") val imageUrl: String?
)