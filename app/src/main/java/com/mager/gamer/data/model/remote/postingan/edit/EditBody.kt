package com.mager.gamer.data.model.remote.postingan.edit


import com.google.gson.annotations.SerializedName

data class EditBody(
    @SerializedName("postText")
    val postText: String,
    @SerializedName("visibility")
    val visibility: Boolean
)