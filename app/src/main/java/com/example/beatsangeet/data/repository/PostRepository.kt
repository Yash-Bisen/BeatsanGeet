package com.example.beatsangeet.data.repository

import android.util.Log
import com.example.beatsangeet.data.model.ApiData
import com.example.beatsangeet.data.model.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PostRepository @Inject constructor() {
    private val api = RetrofitInstance.api

    suspend fun getPosts(): ApiData {
        return api.getPosts()
    }

    suspend fun getSanamSongs(): ApiResponse {
        return api.getSanamSongs()
    }

    suspend fun getArijitSongs(): ApiResponse {
        return api.getArijitSongs()
    }

     fun getAutoComplete(query: String): Call<ApiResponse>{
        return api.getAutoComplete(query)
    }

//    fun makePostRequest(query: String) : ApiResponse{
//        val call = api.getAutoComplete(query)
//        call.enqueue(object : Callback<ApiResponse> {
//            override fun onResponse(
//                call: Call<ApiResponse>,
//                response: Response<ApiResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val autoCompleteResponse = response.body()
//                     // Pass the response to the caller
//                } else {
//                    println("Request failed with code: ${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                t.printStackTrace()
//                 // Pass null on failure
//            }
//        })
//        return call.execute().body()!!
//    }
}