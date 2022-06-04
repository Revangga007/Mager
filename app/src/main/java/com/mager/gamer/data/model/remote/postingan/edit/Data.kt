package com.mager.gamer.data.model.remote.postingan.edit


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.mager.gamer.data.model.remote.postingan.get.KomentarBy
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    @SerializedName("createdBy")
    val createdBy: CreatedBy,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("deleted_date")
    val deletedDate: String?,
    @SerializedName("draft")
    val draft: Boolean,
    @SerializedName("files")
    val files: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("jumlahKomentar")
    val jumlahKomentar: Int,
    @SerializedName("jumlahLike")
    val jumlahLike: Int,
    @SerializedName("komentarBy")
    val komentarBy: List<KomentarBy>,
    @SerializedName("likedBy")
    val likedBy: List<LikedBy>,
    @SerializedName("linkLivestream")
    val linkLivestream: String?,
    @SerializedName("linkPostingan")
    val linkPostingan: String,
    @SerializedName("postText")
    val postText: String,
    @SerializedName("postedIn")
    val postedIn: String?,
    @SerializedName("tipePost")
    val tipePost: String,
    @SerializedName("updated_date")
    val updatedDate: String,
    @SerializedName("visibility")
    val visibility: Boolean
): Parcelable