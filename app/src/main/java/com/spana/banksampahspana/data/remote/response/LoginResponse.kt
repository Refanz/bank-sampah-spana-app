package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("access_token")
    val accessToken: String,

    @field:SerializedName("user")
    val user: User,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("token_type")
    val tokenType: String,

    @field:SerializedName("status")
    val status: String
)

data class LoginErrorResponse(
    @field: SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String
)