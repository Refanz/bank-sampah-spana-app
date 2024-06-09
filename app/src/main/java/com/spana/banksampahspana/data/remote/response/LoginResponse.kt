package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("access_token")
    val accessToken: String,

    @field:SerializedName("data")
    val user: User,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("token_type")
    val tokenType: String,

    @field:SerializedName("status")
    val status: String
)

data class User(

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

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
