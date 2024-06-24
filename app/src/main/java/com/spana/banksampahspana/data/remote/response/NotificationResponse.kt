package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @field:SerializedName("message")
    val message: String
)

data class TokenResponse(
    @field:SerializedName("message")
    val message: String
)
