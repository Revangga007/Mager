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
    val files: List<Any>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("komunitas")
    val komunitas: Any,
    @SerializedName("postText")
    val postText: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_date")
    val updatedDate: String,
    @SerializedName("visibility")
    val visibility: Boolean
)