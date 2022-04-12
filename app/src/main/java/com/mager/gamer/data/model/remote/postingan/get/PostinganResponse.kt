package com.mager.gamer.data.model.remote.postingan.get


import com.google.gson.annotations.SerializedName

data class PostinganResponse(
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalItems")
    val totalItems: Int,
    @SerializedName("totalPages")
    val totalPages: Int
)