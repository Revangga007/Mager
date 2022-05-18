package com.mager.gamer.data.model.remote.postingan.create


import com.google.gson.annotations.SerializedName

data class CreatePostinganResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)