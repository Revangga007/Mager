package com.mager.gamer.data.model.remote.postingan.komentar.get


import com.google.gson.annotations.SerializedName

data class GetKomenResponse(
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