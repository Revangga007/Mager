package com.mager.gamer.data.model.remote.user.edit

import com.google.gson.annotations.SerializedName

class EditUserBody (
    @SerializedName("nama")
    val nama: String?,
    @SerializedName("biodata")
    val biodata: String?,
    @SerializedName("lokasi")
    val lokasi: String?,
    @SerializedName("fotoProfile")
    val fotoProfile: String,
)
