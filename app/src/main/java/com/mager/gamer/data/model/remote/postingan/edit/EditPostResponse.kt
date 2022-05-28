package com.mager.gamer.data.model.remote.postingan.edit


import com.google.gson.annotations.SerializedName

data class EditPostResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)