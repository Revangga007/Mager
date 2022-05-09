package com.mager.gamer.data.model.remote.login

import com.google.gson.annotations.SerializedName

data class LoginBody(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String

)