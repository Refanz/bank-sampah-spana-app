package com.spana.banksampahspana.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class TrashCategoryResponse(

    @field:SerializedName("data")
    val data: List<TrashCategoryItem?>? = null
)

data class TrashCategoryItemResponse(
    @field:SerializedName("data")
    val data: TrashCategoryItem
)

data class TrashCategoryActionResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String
)

@Parcelize
data class TrashCategoryItem(

    @field:SerializedName("unit")
    val unit: String? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
) : Parcelable
