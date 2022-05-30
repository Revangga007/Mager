package com.mager.gamer.data.model.remote.postingan.komentar.get


import com.google.gson.annotations.SerializedName

data class Role(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)