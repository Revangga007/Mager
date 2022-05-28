package com.mager.gamer.data.model.remote.komunitas.join


import com.google.gson.annotations.SerializedName

data class KomunitasRoles(
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted_date")
    val deletedDate: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("jenisRole")
    val jenisRole: String,
    @SerializedName("updated_date")
    val updatedDate: String
)