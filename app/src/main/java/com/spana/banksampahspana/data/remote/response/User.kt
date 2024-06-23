package com.spana.banksampahspana.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    @field:SerializedName("role")
    var role: String = "",

    @field:SerializedName("updated_at")
    var updatedAt: String = "",

    @field:SerializedName("user_detail")
    var userDetail: UserDetail? = null,

    @field:SerializedName("name")
    var name: String = "",

    @field:SerializedName("created_at")
    var createdAt: String = "",

//    @field:SerializedName("email_verified_at")
//    var emailVerifiedAt: String = "",

    @field:SerializedName("id")
    var id: Int = 0,

    @field:SerializedName("email")
    var email: String = ""
) : Parcelable