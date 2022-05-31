package com.mager.gamer.data.model.remote.postingan.komentar.del


import com.google.gson.annotations.SerializedName

data class KomentarDelResponse(
    @SerializedName("data")
    val `data`: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)