package com.mager.gamer.data.model.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "postingan")
@Parcelize
data class PostinganEntity(
    @ColumnInfo(name = "created_date")
    @SerializedName("created_date")
    val createdDate: String,
    @ColumnInfo(name = "deleted_date")
    @SerializedName("deleted_date")
    val deletedDate: String,
    @SerializedName("draft")
    val draft: Boolean,
//    @SerializedName("files")
//    val files: List<Any>,
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int,
//    @SerializedName("komunitas")
//    val komunitas: Any,
    @SerializedName("postText")
    val postText: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_date")
    val updatedDate: String,
    @SerializedName("visibility")
    val visibility: Boolean
) : Parcelable
