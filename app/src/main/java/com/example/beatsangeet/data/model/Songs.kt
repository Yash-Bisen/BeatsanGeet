package com.example.beatsangeet.data.model


data class Songs(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val genre: String,
    val source: String,
    val image: String,
    val trackNo: Int,
    val totalTrackCount: Int,
    val duration: Int,
    val site: String,
)