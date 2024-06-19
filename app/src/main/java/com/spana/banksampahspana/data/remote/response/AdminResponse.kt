package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class AdminResponse(

    @field:SerializedName("admin_detail")
    val adminDetail: AdminDetail,

    @field:SerializedName("user")
    val admin: Admin,

    @field:SerializedName("status")
    val status: String
)
