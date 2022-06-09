package com.mager.gamer.data.model.remote.komunitas.getjoined


import com.google.gson.annotations.SerializedName
import com.mager.gamer.data.model.remote.komunitas.get.Komunitas

data class Content(
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted_date")
    val deletedDate: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("komunitas")
    val komunitas: Komunitas,
    @SerializedName("komunitasRoles")
    val komunitasRoles: KomunitasRoles,
    @SerializedName("updated_date")
    val updatedDate: String
)