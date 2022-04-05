package com.mager.gamer.data.remote

import com.mager.gamer.data.model.remote.postingan.PostinganResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/mager/postingan")
    suspend fun getPostingan(

    ) : ApiResponse<PostinganResponse>
}