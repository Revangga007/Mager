package com.mager.gamer.data.model.remote.password


import com.google.gson.annotations.SerializedName

data class ForgetPassBody(
    @SerializedName("email")
    val email: String
)