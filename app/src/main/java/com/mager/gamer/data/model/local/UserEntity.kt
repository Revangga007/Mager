package com.mager.gamer.data.model.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity("user")
@Parcelize
data class UserEntity (
    @ColumnInfo("id")
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @ColumnInfo("name")
    @SerializedName("name")
    val name: String,
    @ColumnInfo("email")
    @SerializedName("email")
    val email: String,
    @ColumnInfo("password")
    @SerializedName("password")
    val password: String,
    @ColumnInfo("photo_Profile")
    @SerializedName("photo_profile")
    val photoProfile: String,
    @ColumnInfo("photo_header")
    @SerializedName("photo_header")
    val photoHeader: String,
    @ColumnInfo("bio")
    @SerializedName("bio")
    val bio:String


        ):Parcelable