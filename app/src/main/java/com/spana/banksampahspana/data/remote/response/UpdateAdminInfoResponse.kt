package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateAdminInfoResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("user")
    val user: Admin,

    @field:SerializedName("status")
    val status: String
)
