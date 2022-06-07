package com.mager.gamer.data.model.remote.user.follow


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Preferensi(
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
): Parcelable