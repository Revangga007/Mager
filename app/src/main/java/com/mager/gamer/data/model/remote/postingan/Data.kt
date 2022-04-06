package com.mager.gamer.data.model.remote.postingan


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted_date")
    val deletedDate: Any,
    @SerializedName("draft")
    val draft: Boolean,
    @SerializedName("files")
    val files: List<File>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("komunitas")
    val komunitas: Any,
    @SerializedName("linkLivestream")
    val linkLivestream: Any,
    @SerializedName("linkPostingan")
    val linkPostingan: String,
    @SerializedName("postText")
    val postText: String,
    @SerializedName("updated_date")
    val updatedDate: String,
    @SerializedName("visibility")
    val visibility: Boolean
)