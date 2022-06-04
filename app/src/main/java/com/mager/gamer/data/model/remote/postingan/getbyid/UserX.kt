package com.mager.gamer.data.model.remote.postingan.getbyid


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserX(
    @SerializedName("biodata")
    val biodata: String,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted_date")
    val deletedDate: String?,
    @SerializedName("email")
    val email: String,
    @SerializedName("fotoHeader")
    val fotoHeader: String?,
    @SerializedName("fotoProfile")
    val fotoProfile: String?,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isVerified")
    val isVerified: Boolean,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("otp")
    val otp: String?,
    @SerializedName("otpExpiredDate")
    val otpExpiredDate: String?,
    @SerializedName("password")
    val password: String,
    @SerializedName("preferensi")
    val preferensi: List<PreferensiXX>,
    @SerializedName("roles")
    val roles: List<RoleXX>,
    @SerializedName("updated_date")
    val updatedDate: String,
    @SerializedName("username")
    val username: String
): Parcelable