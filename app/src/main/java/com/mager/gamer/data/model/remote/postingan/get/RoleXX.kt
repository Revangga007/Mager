package com.mager.gamer.data.model.remote.postingan.get


import com.google.gson.annotations.SerializedName

data class RoleXX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)