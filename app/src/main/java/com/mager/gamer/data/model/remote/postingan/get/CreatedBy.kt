package com.mager.gamer.data.model.remote.postingan.get


import com.google.gson.annotations.SerializedName

data class CreatedBy(
    @SerializedName("biodata")
    val biodata: String,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted_date")
    val deletedDate: Any,
    @SerializedName("email")
    val email: String,
    @SerializedName("fotoHeader")
    val fotoHeader: Any,
    @SerializedName("fotoProfile")
    val fotoProfile: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("updated_date")
    val updatedDate: String,
    @SerializedName("username")
    val username: String
)