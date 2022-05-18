package com.mager.gamer.data.model.remote.postingan.create


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("createdBy")
    val createdBy: CreatedBy,
    @SerializedName("created_date")
    val createdDate: String,
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
    val komentarBy: Any,
    @SerializedName("likedBy")
    val likedBy: Any,
    @SerializedName("linkLivestream")
    val linkLivestream: String,
    @SerializedName("linkPostingan")
    val linkPostingan: String,
    @SerializedName("postText")
    val postText: String,
    @SerializedName("postedIn")
    val postedIn: Any,
    @SerializedName("tipePost")
    val tipePost: String,
    @SerializedName("updated_date")
    val updatedDate: String,
    @SerializedName("visibility")
    val visibility: Boolean
)