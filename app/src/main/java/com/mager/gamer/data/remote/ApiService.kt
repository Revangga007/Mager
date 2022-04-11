package com.mager.gamer.data.remote

import com.mager.gamer.data.model.remote.postingan.PostinganResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("mager/postingan")
    suspend fun getPostingan(
        @Query("size") size: Int,
        @Query("page") page: Int
    ) : ApiResponse<PostinganResponse>

//    @Multipart
//    @POST
//    suspend fun ():ApiResponse<PostinganResponse>
}