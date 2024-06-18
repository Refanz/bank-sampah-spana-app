package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class TotalWithdrawalResponse(

    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("status")
    val status: String
)
