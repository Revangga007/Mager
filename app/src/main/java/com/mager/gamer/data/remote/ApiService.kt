package com.mager.gamer.data.remote



import com.mager.gamer.data.model.remote.komunitas.get.KomunitasResponse
import com.mager.gamer.data.model.remote.komunitas.getjoined.JoinedResponse
import com.mager.gamer.data.model.remote.komunitas.join.JoinCommunityResponse
import com.mager.gamer.data.model.remote.login.LoginResponse
import com.mager.gamer.data.model.remote.password.ForgetPassBody
import com.mager.gamer.data.model.remote.password.ForgetPassResponse
import com.mager.gamer.data.model.remote.postingan.create.CreatePostinganResponse
import com.mager.gamer.data.model.remote.postingan.delete.DeleteResponse
import com.mager.gamer.data.model.remote.postingan.edit.EditBody
import com.mager.gamer.data.model.remote.postingan.edit.EditPostResponse
import com.mager.gamer.data.model.remote.postingan.get.PostinganResponse
import com.mager.gamer.data.model.remote.postingan.komentar.del.KomentarDelResponse
import com.mager.gamer.data.model.remote.postingan.komentar.get.GetKomenResponse
import com.mager.gamer.data.model.remote.postingan.komentar.post.KomentarBody
import com.mager.gamer.data.model.remote.postingan.komentar.post.KomentarPostinganResponse
import com.mager.gamer.data.model.remote.postingan.like.LikePostinganResponse
import com.mager.gamer.data.model.remote.postingan.post.CreatePostBody
import com.mager.gamer.data.model.remote.register.RegisterBody
import com.mager.gamer.data.model.remote.register.RegisterResponse
import com.mager.gamer.data.model.remote.upload.UploadResponse
import com.mager.gamer.data.model.remote.user.detail.UserDetailResponse
import com.mager.gamer.data.model.remote.user.edit.EditUserBody
import com.mager.gamer.data.model.remote.user.edit.EditUserResponse
import com.mager.gamer.data.model.remote.user.follow.FollowResponse
import com.mager.gamer.data.model.remote.user.getfollowers.GetFolResponse
import com.mager.gamer.data.model.remote.user.getfollowing.GetFollowingResponse
import com.skydoves.sandwich.ApiResponse
import okhttp3.MultipartBody
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
        @Query("idUser") idUser: Int?,
    ) : ApiResponse<PostinganResponse>

    @POST("mager/postingan")
    suspend fun createPostingan(
        @Query("idUser") idUser: Int,
        @Body body: CreatePostBody
    ):ApiResponse<CreatePostinganResponse>

    @Multipart
    @POST("mager/uploadFiles")
    suspend fun uploadFiles(
        @Part file: MultipartBody.Part
    ): ApiResponse<UploadResponse>

    @POST("mager/like")
    suspend fun likePostingan(
        @Query("idPostingan") idPostingan: Int,
        @Query("idUser") idUser: Int,
    ): ApiResponse<LikePostinganResponse>

    @PUT("mager/postingan/{idPostingan}")
    suspend fun editPost(
        @Path("idPostingan") idPostingan: Int,
        @Query("idUser") idUser: Int,
        @Body body: EditBody
    ):ApiResponse<EditPostResponse>

    @GET("mager/komunitas/list")
    suspend fun getKomunitas(
        @Query("size") size: Int,
        @Query("page") page: Int,
    ) : ApiResponse<KomunitasResponse>

    @GET("mager/komunitas/joined/{idUser}")
    suspend fun getJoinedKomunitas(
        @Path("idUser") idUser: Int,
        @Query("size") size: Int = 100,
        @Query("page") page: Int = 0
    ) : ApiResponse<JoinedResponse>

    @POST("mager/komunitas/join/{idUser}/{idCommunity}")
    suspend fun joinCommunity(
        @Path("idUser") idUser: Int,
        @Path("idCommunity") idCommunity: Int
    ): ApiResponse<JoinCommunityResponse>

    @FormUrlEncoded
    @POST("mager/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ) : ApiResponse<LoginResponse>

    @POST("mager/user")
    suspend fun register(
        @Body body: RegisterBody
    ):ApiResponse<RegisterResponse>

    @POST("mager/forget-password/register-tymeleaf")
    suspend fun forgetPass(
        @Body body: ForgetPassBody
    ):ApiResponse<ForgetPassResponse>

    @POST("mager/komentar")
    suspend fun komentar(
        @Query("idUser") idUser: Int,
        @Query("idPostingan") idPostingan: Int,
        @Body body : KomentarBody
    ):ApiResponse<KomentarPostinganResponse>

    @DELETE("mager/postingan/{idPostingan}")
    suspend fun deletePost(
        @Path("idPostingan") idPostingan: Int
    ):ApiResponse<DeleteResponse>

    @GET("mager/user/{idUser}")
    suspend fun userDetail(
        @Path("idUser") idUser: Int
    ):ApiResponse<UserDetailResponse>

    @DELETE("mager/komentar/{idKomentar}")
    suspend fun delKomentar(
        @Path("idKomentar") idkomentar: Int
    ):ApiResponse<KomentarDelResponse>

    @GET("mager/komentar")
    suspend fun getKomentar(
        @Query("idPostingan") idPostingan: Int
    ):ApiResponse<GetKomenResponse>

    @POST("mager/user/{{idFollower}}/follow/{{idFollowing}}")
    suspend fun postFollow(
        @Path("idFollower") idFollower: Int,
        @Path("idFollowing") idFollowing: Int
    ):ApiResponse<FollowResponse>

    @GET("mager/user/{idUser}/following")
    suspend fun getFollowing(
        @Path("idUser") idUser: Int,
       @Query("size") size: Int,
        @Query("page") page: Int
    ):ApiResponse<GetFollowingResponse>

    @GET("mager/user/{idUser}/follower")
    suspend fun getFollower(
        @Path("idUser") idUser: Int,
       @Query("size") size: Int,
        @Query("page") page: Int
    ):ApiResponse<GetFolResponse>

    @PUT("mager/user/{idUser)")
    suspend fun editUser(
        @Path("idUser") idUser: Int,
        @Body body: EditUserBody
    ): ApiResponse<EditUserResponse>

    @POST("mager/user/{idFollower}/follow/{idFollowing}")
    suspend fun follow(
        @Path("idFollower") idFollower: Int,
        @Path("idFollowing") idFollowing: Int
    ): ApiResponse<FollowResponse>

}