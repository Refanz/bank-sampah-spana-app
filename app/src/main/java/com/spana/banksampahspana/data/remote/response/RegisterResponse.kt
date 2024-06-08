package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName
import com.spana.banksampahspana.data.model.User

data class RegisterResponse(
    @field:SerializedName("access_token")
    val accessToken: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("data")
    val data: User,

    @field:SerializedName("token_type")
    val tokenType: String,
)