package com.example.cats.model

//@Parcelize
data class Cat(
    val id: String,
    val url: String,
    private val width: Int,
    private val height: Int
)