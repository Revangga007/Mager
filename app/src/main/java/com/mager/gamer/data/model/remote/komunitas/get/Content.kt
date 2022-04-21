package com.mager.gamer.data.model.remote.komunitas.get


import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("acceptance")
    val acceptance: Boolean,
    @SerializedName("banner")
    val banner: String,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted_date")
    val deletedDate: Any,
    @SerializedName("deskripsi")
    val deskripsi: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("jumlahAnggota")
    val jumlahAnggota: Int,
    @SerializedName("lokasi")
    val lokasi: String,
    @SerializedName("namaKomunitas")
    val namaKomunitas: String,
    @SerializedName("updated_date")
    val updatedDate: String
)