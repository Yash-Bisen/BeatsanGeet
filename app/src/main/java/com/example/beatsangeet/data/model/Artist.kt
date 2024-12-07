package com.example.beatsangeet.data.model

data class Artist(
    val id: Int,
    val name: String,
    val sex: String,
    val age: Int,
    val description: String,
    val puppyImageId: Int = 0
)