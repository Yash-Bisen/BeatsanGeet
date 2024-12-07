package com.example.beatsangeet.data.apinetwork

import com.example.beatsangeet.data.model.ApiData
import com.example.beatsangeet.data.model.ApiResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @GET("api.php?__call=content.getHomepageData")
    suspend fun getPosts(): ApiData

    @GET("api.php?__call=autocomplete.get&_format=json&_marker=0&cc=in&includeMetaTags=1&query=kailashkher")
    suspend fun getSanamSongs(): ApiResponse

    @GET("api.php?__call=autocomplete.get&_format=json&_marker=0&cc=in&includeMetaTags=1&query=Arijit")
    suspend fun getArijitSongs(): ApiResponse

    @GET("api.php?__call=autocomplete.get&_format=json&_marker=0&cc=in&includeMetaTags=1")
    fun getAutoComplete(@Query("query") query: String): Call<ApiResponse>


}

