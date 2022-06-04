package com.mager.gamer.data.model.remote.postingan.getbyid


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LikedBy(
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted_date")
    val deletedDate: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("reaction")
    val reaction: Boolean,
    @SerializedName("updated_date")
    val updatedDate: String,
    @SerializedName("user")
    val user: UserX
): Parcelable