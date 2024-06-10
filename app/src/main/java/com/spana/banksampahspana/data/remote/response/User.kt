package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("role")
	val role: String,

	@field:SerializedName("updated_at")
	val updatedAt: String,

	@field:SerializedName("user_detail")
	val userDetail: UserDetail,

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