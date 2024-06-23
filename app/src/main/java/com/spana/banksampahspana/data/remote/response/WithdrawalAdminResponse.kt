package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class WithdrawalAdminResponse(

    @field:SerializedName("data")
    val data: List<WithdrawalAdmin>,

    @field:SerializedName("status")
    val status: String
)

data class UpdateWithdrawalAdminResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: String
)

data class WithdrawalAdmin(

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("withdrawal_id")
    val withdrawalId: Int,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("nis")
    val nis: String,

    @field:SerializedName("payment")
    val payment: String,

    @field:SerializedName("withdrawal_date")
    val withdrawalDate: String,

    @field:SerializedName("total_withdrawal")
    val totalWithdrawal: Int,

    @field:SerializedName("status")
    val status: String
)
