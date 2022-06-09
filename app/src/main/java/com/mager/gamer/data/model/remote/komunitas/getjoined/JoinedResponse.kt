package com.mager.gamer.data.model.remote.komunitas.getjoined


import com.google.gson.annotations.SerializedName

data class JoinedResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)