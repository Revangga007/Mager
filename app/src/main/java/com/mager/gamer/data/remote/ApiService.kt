package com.mager.gamer.data.remote



import com.mager.gamer.data.model.remote.komunitas.get.KomunitasResponse
import com.mager.gamer.data.model.remote.login.LoginBody
import com.mager.gamer.data.model.remote.login.LoginResponse
import com.mager.gamer.data.model.remote.postingan.get.PostinganResponse
import com.mager.gamer.data.model.remote.postingan.like.LikePostinganResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface ApiService {

    @GET("mager/postingan")
    suspend fun getPostingan(
        @Query("size") size: Int,
        @Query("page") page: Int,
        @Query("sort") sort: String?,
        @Query("tipeSort") tipeSort: String?,
        @Query("tipeData") tipeData: String?,
        @Query("idKomunitas") idKomunitas: Int?,
        //@Header("Authorization") token: String?
    ) : ApiResponse<PostinganResponse>

//    @Multipart
//    @POST
//    suspend fun createPostingan(
//        @Url url: String,
//        @Part("postingan") postingan: Postingan,
//        @Part uploadFiles: MultipartBody.Part
//    ):ApiResponse<PostinganResponse>

    @POST("mager/like")
    suspend fun likePostingan(
        @Query("idPostingan") idPostingan: Int,
        @Query("idUser") idUser: Int,
        //@Header("Authorization") token: String?

    ): ApiResponse<LikePostinganResponse>

    @GET("mager/komunitas")
    suspend fun getKomunitas(
        @Query("size") size: Int,
        @Query("page") page: Int
    ) : ApiResponse<KomunitasResponse>

    @FormUrlEncoded
    @POST("mager/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ) : ApiResponse<LoginResponse>
}