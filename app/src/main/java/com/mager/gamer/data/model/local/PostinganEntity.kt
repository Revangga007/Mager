package com.mager.gamer.data.model.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "postingan")
@Parcelize
data class PostinganEntity (
    @ColumnInfo("id")
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @ColumnInfo("id_user")
    @SerializedName("id_user")
    val idUser: Int,
    @ColumnInfo("id_komunitas")
    @SerializedName("id_komunitas")
    val idKomunitas: Int,
    @ColumnInfo("title")
    @SerializedName("title")
    val title: String,
    @ColumnInfo("post_text")
    @SerializedName("post_text"),
    val public: Boolean,
    @ColumnInfo("draft")
    @SerializedName("draft")
    val draft: Boolean
        ):Parcelable