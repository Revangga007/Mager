package com.mager.gamer.data.model.remote.postingan.post.getAll


import com.google.gson.annotations.SerializedName

data class KomentarBy(
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