package com.mager.gamer.data.model.remote.komunitas.getjoined


import com.google.gson.annotations.SerializedName

data class Kategori(
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted_date")
    val deletedDate: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("namaGame")
    val namaGame: String,
    @SerializedName("updated_date")
    val updatedDate: String
)