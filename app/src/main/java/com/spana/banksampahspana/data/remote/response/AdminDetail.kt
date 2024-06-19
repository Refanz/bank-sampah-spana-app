package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class AdminDetail(

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("nip")
    val nip: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: Int,
)