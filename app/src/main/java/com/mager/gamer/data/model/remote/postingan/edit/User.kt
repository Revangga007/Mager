package com.mager.gamer.data.model.remote.postingan.edit


import com.google.gson.annotations.SerializedName

data class User(
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
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isVerified")
    val isVerified: Boolean,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("otp")
    val otp: Any,
    @SerializedName("otpExpiredDate")
    val otpExpiredDate: Any,
    @SerializedName("password")
    val password: String,
    @SerializedName("preferensi")
    val preferensi: List<PreferensiX>,
    @SerializedName("roles")
    val roles: List<RoleX>,
    @SerializedName("updated_date")
    val updatedDate: String,
    @SerializedName("username")
    val username: String
)