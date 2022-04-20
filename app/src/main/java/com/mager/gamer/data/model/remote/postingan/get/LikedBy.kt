package com.mager.gamer.data.model.remote.postingan.get


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class LikedBy(
    @SerializedName("created_date")
    val createdDate: Date,
    @SerializedName("deleted_date")
    val deletedDate: Date?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("reaction")
    val reaction: Boolean,
    @SerializedName("updated_date")
    val updatedDate: Date,
    @SerializedName("user")
    val user: UserX
) : Parcelable