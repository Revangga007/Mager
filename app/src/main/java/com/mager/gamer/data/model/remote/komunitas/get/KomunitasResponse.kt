package com.mager.gamer.data.model.remote.komunitas.get


import com.google.gson.annotations.SerializedName


data class KomunitasResponse(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)