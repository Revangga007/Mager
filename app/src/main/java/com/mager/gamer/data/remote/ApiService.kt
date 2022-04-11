package com.mager.gamer.data.remote

import com.mager.gamer.data.model.remote.postingan.get.PostinganResponse
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    @GET("mager/postingan")
    suspend fun getPostingan(
        @Query("size") size: Int,
        @Query("page") page: Int
    ) : ApiResponse<PostinganResponse>

    @Multipart
    @POST
    suspend fun createPostingan(
        @Url url: String,
        @Part("postingan") postingan: Map<String, String>,
        @Part uploadFiles: MultipartBody.Part
    ):ApiResponse<PostinganResponse>
}