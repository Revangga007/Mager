package com.mager.gamer.data.model.remote.postingan.komentar


import com.google.gson.annotations.SerializedName

data class KomentarPostinganResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)