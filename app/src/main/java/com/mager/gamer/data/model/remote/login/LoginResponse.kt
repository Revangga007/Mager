package com.mager.gamer.data.model.remote.login


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("idUser")
    val idUser: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("username")
    val username: String
)