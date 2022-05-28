package com.mager.gamer.data.model.remote.komunitas.join


import com.google.gson.annotations.SerializedName

data class JoinCommunityResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)