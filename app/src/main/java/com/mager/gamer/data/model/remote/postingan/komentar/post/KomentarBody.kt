package com.mager.gamer.data.model.remote.postingan.komentar.post


import com.google.gson.annotations.SerializedName

data class KomentarBody(
    @SerializedName("isiKomentar")
    val isiKomentar: String
)