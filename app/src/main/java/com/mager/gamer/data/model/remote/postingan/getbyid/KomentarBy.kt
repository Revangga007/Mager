package com.mager.gamer.data.model.remote.postingan.getbyid


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class KomentarBy(
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted_date")
    val deletedDate: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("isiKomentar")
    val isiKomentar: String,
    @SerializedName("updated_date")
    val updatedDate: String,
    @SerializedName("user")
    val user: User
): Parcelable