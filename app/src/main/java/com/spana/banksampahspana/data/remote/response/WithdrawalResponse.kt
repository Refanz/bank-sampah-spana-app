package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class WithdrawalResponse(

    @field:SerializedName("data")
    val data: Withdrawal
)

data class Withdrawal(

    @field:SerializedName("total_balance")
    val totalBalance: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("withdrawal_date")
    val withdrawalDate: String,

    @field:SerializedName("status")
    val status: String
)
