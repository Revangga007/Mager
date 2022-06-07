package com.mager.gamer.data.model.remote.user.follow


import com.google.gson.annotations.SerializedName

data class FollowResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)