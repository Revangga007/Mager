package com.mager.gamer.data.model.remote.postingan.delete


import com.google.gson.annotations.SerializedName

data class DeleteResponse(
    @SerializedName("data")
    val `data`: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)