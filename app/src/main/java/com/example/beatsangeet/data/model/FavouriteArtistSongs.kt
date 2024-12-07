package com.example.beatsangeet.data.model


data class ApiResponse(
    val albums: AlbumsResponse,
    val songs: SongsResponse,
    val playlists: PlaylistsResponse,
    val artists: ArtistsResponse,
    val topquery: TopqueryResponse,
    val shows: ShowsResponse,
)

data class AlbumsResponse(
    val data: List<Album>,
    val position: Int
)

data class Album(
    val id: String,
    val title: String,
    val image: String,
    val music: String,
    val url: String,
    val type: String,
    val description: String,
    val ctr: Int,
    val position: Int,
    val more_info: MoreInfo
)

data class MoreInfo(
    val year: String,
    val is_movie: String,
    val language: String,
    val song_pids: String
)

data class SongsResponse(
    val data: List<Song>,
    val position: Int
)

data class Song(
    val id: String,
    val title: String,
    val image: String,
    val album: String,
    val url: String,
    val type: String,
    val description: String,
    val ctr: Int,
    val position: Int,
    val more_info: SongMoreInfo
)

data class SongMoreInfo(
    val vcode: String,
    val vlink: String,
    val primary_artists: String,
    val singers: String,
    val video_available: String?,
    val triller_available: Boolean,
    val language: String
)

data class PlaylistsResponse(
    val data: List<Playlist>,
    val position: Int
)

data class Playlist(
    val id: String,
    val title: String,
    val image: String,
    val extra: String,
    val url: String,
    val language: String,
    val type: String,
    val description: String,
    val position: Int,
    val more_info: Any?
)

data class ArtistsResponse(
    val data: List<Artists>,
    val position: Int
)

data class Artists(
    val id: String,
    val title: String,
    val image: String,
    val extra: String,
    val url: String,
    val type: String,
    val description: String,
    val ctr: Int,
    val entity: Int,
    val position: Int
)

data class TopqueryResponse(
    val data: List<TopQuery>,
    val position: Int
)

data class TopQuery(
    val id: String,
    val title: String,
    val image: String,
    val extra: String,
    val url: String,
    val type: String,
    val description: String,
    val ctr: Int,
    val entity: Int,
    val position: Int
)

data class ShowsResponse(
    val data: List<Show>,
    val position: Int
)

data class Show(
    val id: String,
    val title: String,
    val image: String,
    val type: String,
    val season_number: Int,
    val description: String,
    val url: String,
    val position: Int
)




