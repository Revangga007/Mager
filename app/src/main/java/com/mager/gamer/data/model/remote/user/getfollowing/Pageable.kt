package com.mager.gamer.data.model.remote.user.getfollowing


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pageable(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("pageNumber")
    val pageNumber: Int,
    @SerializedName("pageSize")
    val pageSize: Int,
    @SerializedName("paged")
    val paged: Boolean,
    @SerializedName("sort")
    val sort: Sort,
    @SerializedName("unpaged")
    val unpaged: Boolean
): Parcelable