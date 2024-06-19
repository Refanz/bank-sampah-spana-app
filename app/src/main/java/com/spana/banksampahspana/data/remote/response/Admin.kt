package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class Admin(

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("admin_detail")
    val adminDetail: AdminDetail,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("email_verified_at")
    val emailVerifiedAt: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: String
)