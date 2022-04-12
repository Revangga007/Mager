package com.mager.gamer.data.model.remote.postingan.post.getId


import com.google.gson.annotations.SerializedName

data class DetailPostingan(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)