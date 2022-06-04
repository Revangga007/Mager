package com.mager.gamer.data.model.remote.user.getfollowing


import com.google.gson.annotations.SerializedName

data class GetFollowingResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)