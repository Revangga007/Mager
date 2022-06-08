package com.mager.gamer.data.model.remote.user.getfollowers


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mager.gamer.data.model.remote.user.Content
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("empty")
    val empty: Boolean,
    @SerializedName("first")
    val first: Boolean,
    @SerializedName("last")
    val last: Boolean,
    @SerializedName("number")
    val number: Int,
    @SerializedName("numberOfElements")
    val numberOfElements: Int,
    @SerializedName("pageable")
    val pageable: Pageable,
    @SerializedName("size")
    val size: Int,
    @SerializedName("sort")
    val sort: SortX,
    @SerializedName("totalElements")
    val totalElements: Int,
    @SerializedName("totalPages")
    val totalPages: Int
): Parcelable