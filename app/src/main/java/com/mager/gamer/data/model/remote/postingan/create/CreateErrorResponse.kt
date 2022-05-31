package com.mager.gamer.data.model.remote.postingan.create

import com.google.gson.annotations.SerializedName

class CreateErrorResponse(
    @SerializedName("message")
    val message: String
)