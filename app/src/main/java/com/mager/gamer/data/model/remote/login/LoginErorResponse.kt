package com.mager.gamer.data.model.remote.login


import com.google.gson.annotations.SerializedName

data class LoginErorResponse(
    @SerializedName("message")
    val message: String
)