package com.mager.gamer.data.model.remote.postingan.like


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("biodata")
    val biodata: Any,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted_date")
    val deletedDate: Any,
    @SerializedName("email")
    val email: String,
    @SerializedName("fotoHeader")
    val fotoHeader: String,
    @SerializedName("fotoProfile")
    val fotoProfile: String,
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