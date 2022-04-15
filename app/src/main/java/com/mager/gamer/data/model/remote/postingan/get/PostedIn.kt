package com.mager.gamer.data.model.remote.postingan.get


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class PostedIn(
    @SerializedName("acceptance")
    val acceptance: Boolean,
    @SerializedName("banner")
    val banner: String?,
    @SerializedName("created_date")
    val createdDate: Date,
    @SerializedName("deleted_date")
    val deletedDate: Date?,
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
    val updatedDate: Date
): Parcelable