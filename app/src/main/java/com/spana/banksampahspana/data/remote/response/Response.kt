package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("errors")
	val errors: Errors
)

data class Errors(

	@field:SerializedName("password")
	val password: List<String>,

	@field:SerializedName("gender")
	val gender: List<String>,

	@field:SerializedName("phone")
	val phone: List<String>,

	@field:SerializedName("name")
	val name: List<String>,

	@field:SerializedName("nis")
	val nis: List<String>,

	@field:SerializedName("email")
	val email: List<String>,

	@field:SerializedName("payment_method")
	val paymentMethod: List<String>
)
