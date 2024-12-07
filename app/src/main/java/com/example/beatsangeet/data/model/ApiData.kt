package com.example.beatsangeet.data.model
import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName

data class ApiData(
    val new_albums: List<NewAlbum>,
    val featured_playlists: List<FeaturedPlaylist>,
    val genres: List<Genre>
)

data class NewAlbum(
    val query: String,
    val text: String,
    val year: String,
    val image: String,
    val albumid: String,
    val title: String,
    val Artist: ArtistX,
    val weight: Int,
    val language: String
)

data class ArtistX(
    val music: List<Music>
)

data class Music(
    val id: String,
    val name: String
)

data class FeaturedPlaylist(
    val count: Int,
    val data_type: String,
    val firstname: String,
    val follower_count: String,
    val image: String,
    val last_updated: Int,
    val listid: String,
    val listname: String,
    val perma_url: String,
    val secondary_subtitle: String,
    val sponsored: Boolean,
    val uid: String,
    var color: Color = Color.Transparent
)

data class Genre(
    val image: String,
    val title: String,
    val tags: String,
    val about: String
)