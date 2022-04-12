package com.mager.gamer.data.model.remote.postingan.get


import com.google.gson.annotations.SerializedName
import java.util.*

data class Data(
    @SerializedName("createdBy")
    val createdBy: CreatedBy,
    @SerializedName("created_date")
    val createdDate: Date,
    @SerializedName("deleted_date")
    val deletedDate: Any,
    @SerializedName("draft")
    val draft: Boolean,
    @SerializedName("files")
    val files: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("jumlahKomentar")
    val jumlahKomentar: Int,
    @SerializedName("jumlahLike")
    val jumlahLike: Int,
    @SerializedName("komentarBy")
    val komentarBy: List<Any>,
    @SerializedName("likedBy")
    val likedBy: List<LikedBy>,
    @SerializedName("linkLivestream")
    val linkLivestream: Any,
    @SerializedName("linkPostingan")
    val linkPostingan: String,
    @SerializedName("postText")
    val postText: String,
    @SerializedName("postedIn")
    val postedIn: Any,
    @SerializedName("updated_date")
    val updatedDate: String,
    @SerializedName("visibility")
    val visibility: Boolean
)