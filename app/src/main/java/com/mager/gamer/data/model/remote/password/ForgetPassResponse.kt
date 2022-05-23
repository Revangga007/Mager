package com.mager.gamer.data.model.remote.password


import com.google.gson.annotations.SerializedName

data class ForgetPassResponse(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)