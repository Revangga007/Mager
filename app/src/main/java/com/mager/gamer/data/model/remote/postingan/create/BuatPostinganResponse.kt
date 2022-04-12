package com.mager.gamer.data.model.remote.postingan.create


import com.google.gson.annotations.SerializedName

data class BuatPostinganResponse(
    @SerializedName("postingan")
    val postingan: Postingan,
    @SerializedName("uploadsFiles")
    val uploadsFiles: String
)