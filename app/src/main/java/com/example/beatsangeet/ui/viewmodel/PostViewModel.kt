package com.example.beatsangeet.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.beatsangeet.data.model.Album
import com.example.beatsangeet.data.model.AlbumsResponse
import com.example.beatsangeet.data.model.ApiResponse
import com.example.beatsangeet.data.model.Artist
import com.example.beatsangeet.data.model.FeaturedPlaylist
import com.example.beatsangeet.data.model.Genre
import com.example.beatsangeet.data.model.NewAlbum
import com.example.beatsangeet.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor( private val repository : PostRepository) : ViewModel() {

    private val _albums = MutableStateFlow<List<NewAlbum>>(emptyList())
    val albums: StateFlow<List<NewAlbum>> = _albums.asStateFlow()

    private val _albumsKk = MutableStateFlow<List<Album>>(emptyList())
    val albumsKk: StateFlow<List<Album>> = _albumsKk.asStateFlow()

    private val _albumsArijit = MutableStateFlow<List<Album>>(emptyList())
    val albumsArijit: StateFlow<List<Album>> = _albumsArijit.asStateFlow()

    private val _artists = MutableLiveData<ApiResponse?>()
    val artists: MutableLiveData<ApiResponse?> get() = _artists

    private val _fearturedPlayList = MutableStateFlow<List<FeaturedPlaylist>>(emptyList())
    val fearturedPlayList: StateFlow<List<FeaturedPlaylist>> = _fearturedPlayList.asStateFlow()

    private val _genre = MutableStateFlow<List<Genre>>(emptyList())
    val genre: StateFlow<List<Genre>> = _genre.asStateFlow()



    init {
        fetchSanamSongs()
        fetchArijitSongs()
        fetchPosts()
        searchArtists("")
    }

    private fun fetchPosts(){
        viewModelScope.launch {
            try {
                val response = repository.getPosts()
                _albums.value = response.new_albums
                _fearturedPlayList.value = response.featured_playlists
                _genre.value = response.genres
                Log.i("Album...............", albums.value.size.toString())
                Log.i("feature...............", fearturedPlayList.value.size.toString())
                Log.i("genre...............", genre.value.size.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchSanamSongs(){
        viewModelScope.launch {
            try {
                Log.i("AlbumKKKKKKKKKKKKKKKKKKKKK", albumsKk.value.size.toString())
                val response = repository.getSanamSongs()
                _albumsKk.value = response.albums.data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun fetchArijitSongs(){
        viewModelScope.launch {
            try {
                Log.i("AlbumKKKKKKKKKKKKKKKKKKKKK", albumsKk.value.size.toString())
                val response = repository.getArijitSongs()
                _albumsArijit.value = response.albums.data

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun  clearResults(){
        _artists.value = null
    }


    fun searchArtists(query: String) {
         viewModelScope.launch {
             val call = repository.getAutoComplete(query)
             call.enqueue(object : Callback<ApiResponse> {
                 override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                     if (response.isSuccessful) {
                         Log.i("REsponse.body ", response.body().toString())
                         _artists.value = response.body()
                     }
                 }

                 override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                     // Handle error
                 }
             })
         }
    }
}