package com.mager.gamer.data.model.remote.postingan.komentar.post


import com.google.gson.annotations.SerializedName
import com.mager.gamer.data.model.remote.postingan.get.User

data class Data(
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted_date")
    val deletedDate: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isiKomentar")
    val isiKomentar: String,
    @SerializedName("updated_date")
    val updatedDate: String,
    @SerializedName("user")
    val user: User
)