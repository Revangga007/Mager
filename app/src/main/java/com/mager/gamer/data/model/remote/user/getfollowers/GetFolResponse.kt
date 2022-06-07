package com.mager.gamer.data.model.remote.user.getfollowers


import com.google.gson.annotations.SerializedName

data class GetFolResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)