package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class TrashCategoryResponse(

    @field:SerializedName("data")
    val data: List<TrashCategoryItem?>? = null
)

data class TrashCategoryItem(

    @field:SerializedName("unit")
    val unit: String? = null,

    @field:SerializedName("price")
    val price: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)
