package com.mager.gamer.data.model.remote.postingan.like


import com.google.gson.annotations.SerializedName

data class LikePostinganResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)