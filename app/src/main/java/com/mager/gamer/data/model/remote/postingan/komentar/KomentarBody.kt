package com.mager.gamer.data.model.remote.postingan.komentar


import com.google.gson.annotations.SerializedName

data class KomentarBody(
    @SerializedName("isiKomentar")
    val isiKomentar: String
)