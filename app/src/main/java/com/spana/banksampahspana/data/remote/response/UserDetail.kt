package com.spana.banksampahspana.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail(

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("balance")
    val balance: Int,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("phone")
    val phone: String,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("nis")
    val nis: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("class")
    val userClass: Int,

    @field:SerializedName("payment_method")
    val paymentMethod: String
) : Parcelable