package com.mager.gamer.data.model.remote.postingan.like


import com.google.gson.annotations.SerializedName
import com.mager.gamer.data.model.remote.postingan.get.LikedBy

data class LikePostinganResponse(
    @SerializedName("data")
    val `data`: LikedBy,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)