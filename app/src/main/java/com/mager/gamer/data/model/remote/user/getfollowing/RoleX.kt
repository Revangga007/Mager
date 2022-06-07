package com.mager.gamer.data.model.remote.user.getfollowing


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RoleX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
): Parcelable