package com.mager.gamer.data.model.remote.postingan.get


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class UserX(
    @SerializedName("biodata")
    val biodata: String?,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted_date")
    val deletedDate: Date?,
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
) : Parcelable