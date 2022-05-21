package com.mager.gamer.data.model.remote.register


import com.google.gson.annotations.SerializedName

data class RegisterBody(
    @SerializedName("biodata")
    val biodata: String? = null,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String
)