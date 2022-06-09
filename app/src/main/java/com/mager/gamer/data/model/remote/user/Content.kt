package com.mager.gamer.data.model.remote.user


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mager.gamer.data.model.remote.user.getfollowing.UserFollower
import com.mager.gamer.data.model.remote.user.getfollowing.UserFollowing
import kotlinx.parcelize.Parcelize

@Parcelize
data class Content(
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted_date")
    val deletedDate: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("status")
    val status: Boolean = false,
    @SerializedName("updated_date")
    val updatedDate: String,
    @SerializedName("userFollower")
    val userFollower: UserFollower,
    @SerializedName("userFollowing")
    val userFollowing: UserFollowing
): Parcelable