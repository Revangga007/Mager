package com.mager.gamer.data.model.remote.postingan.edit


import com.google.gson.annotations.SerializedName

data class RoleX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)