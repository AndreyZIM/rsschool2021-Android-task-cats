package com.example.cats.model

import android.os.Parcelable

//@Parcelize
data class Cat(
    val id: String,
    val url: String,
    private val width: Int,
    private val height: Int
)