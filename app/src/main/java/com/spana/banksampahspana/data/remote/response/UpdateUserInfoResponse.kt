package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateUserInfoResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("user")
    val user: User,

    @field:SerializedName("status")
    val status: String
)

data class DeleteUserResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String
)
