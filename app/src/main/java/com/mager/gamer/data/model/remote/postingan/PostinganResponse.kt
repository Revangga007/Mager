package com.mager.gamer.data.model.remote.postingan


import com.google.gson.annotations.SerializedName

data class PostinganResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)