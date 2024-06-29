package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChangePasswordResponse(
    @field:SerializedName("message")
    val message: String
)

data class ForgotPasswordResponse(
    @field:SerializedName("status")
    val status: String
)

data class ForgotPasswordErrorResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("errors")
    val errors: ForgotPasswordErrors
)

data class ForgotPasswordErrors(
    @field:SerializedName("email")
    val email: List<String>
)

data class ResetPasswordResponse(
    @field:SerializedName("status")
    val status: String
)
