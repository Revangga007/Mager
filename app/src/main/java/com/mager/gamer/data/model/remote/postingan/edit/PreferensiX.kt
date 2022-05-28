package com.mager.gamer.data.model.remote.postingan.edit


import com.google.gson.annotations.SerializedName

data class PreferensiX(
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted_date")
    val deletedDate: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("namaGame")
    val namaGame: String,
    @SerializedName("updated_date")
    val updatedDate: String
)