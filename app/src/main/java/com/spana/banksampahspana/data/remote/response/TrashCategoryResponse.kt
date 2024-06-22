package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

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

data class TrashCategoryItem(

    @field:SerializedName("unit")
    val unit: String? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)
