package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("user_detail")
	val userDetail: UserDetail,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("status")
	val status: String
)