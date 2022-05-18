package com.mager.gamer.data.model.remote.postingan.post

import com.google.gson.annotations.SerializedName

data class CreatePostBody
    (
    @SerializedName("postText")
    val postText: String? =null,
    @SerializedName("linkLivestream")
    val linkLivestream: String? =null,
    @SerializedName("files")
    val files: String? =null
)