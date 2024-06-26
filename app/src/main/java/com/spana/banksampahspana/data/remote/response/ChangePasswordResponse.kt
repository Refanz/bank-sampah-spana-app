package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(
    @field:SerializedName("message")
    val message: String
)
