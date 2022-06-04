package com.mager.gamer.data.model.remote.postingan.edit


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PreferensiX(
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
):Parcelable