package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class UsersResponse(

    @field:SerializedName("data")
    val data: List<User>,

    @field:SerializedName("status")
    val status: String
)
