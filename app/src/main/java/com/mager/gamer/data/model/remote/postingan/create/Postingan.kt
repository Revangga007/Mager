package com.mager.gamer.data.model.remote.postingan.create


import com.google.gson.annotations.SerializedName

data class Postingan(
    @SerializedName("draft")
    val draft: Boolean,
    @SerializedName("postText")
    val postText: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("visibility")
    val visibility: Boolean
)