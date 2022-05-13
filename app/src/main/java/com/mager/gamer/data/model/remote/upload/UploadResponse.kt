package com.mager.gamer.data.model.remote.upload


import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)