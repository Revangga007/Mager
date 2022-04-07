package com.mager.gamer.data.model.remote.postingan


import com.google.gson.annotations.SerializedName

data class File(
    @SerializedName("fileType")
    val fileType: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("namaFile")
    val namaFile: String,
    @SerializedName("size")
    val size: Int
)