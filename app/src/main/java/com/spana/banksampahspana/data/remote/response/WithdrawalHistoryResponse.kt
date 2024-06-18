package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class WithdrawalHistoryResponse(
    @field:SerializedName("data")
    val data: List<Withdrawal>
)
