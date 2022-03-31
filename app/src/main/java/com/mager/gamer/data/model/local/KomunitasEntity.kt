package com.mager.gamer.data.model.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "komunitas")
@Parcelize
data class KomunitasEntity(
    @ColumnInfo("id")
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @ColumnInfo("id_games")
    @SerializedName("id_games")
    val idGames: Int,
    @ColumnInfo("id_user")
    @SerializedName("id_user")
    val idUser: Int,
    @ColumnInfo("nama_komunitas")
    @SerializedName("nama_komunitas")
    val namaKomunitas: String,
    @ColumnInfo("deskripsi")
    @SerializedName("deskripsi")
    val deskripsi: String,
    @ColumnInfo("location")
    @SerializedName("location")
    val location: String,
    @ColumnInfo("jumlah_anggota")
    @SerializedName("jumlah_anggota")
    val jumlahAnggota: Long,
    @ColumnInfo("acceptance")
    @SerializedName("acceptance")
    val acceptance: Boolean
): Parcelable
