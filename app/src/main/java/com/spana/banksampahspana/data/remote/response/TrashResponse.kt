package com.spana.banksampahspana.data.remote.response

import com.google.gson.annotations.SerializedName

data class TrashResponse(

    @field:SerializedName("data")
    val trash: Trash
)

data class UserTrashResponse(

    @field:SerializedName("data")
    val data: List<Trash>? = null
)

data class Trash(

    @field:SerializedName("total_deposit")
    val totalDeposit: Int,

    @field:SerializedName("trash_type")
    val trashType: String,

    @field:SerializedName("weight")
    val weight: Double,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("created_at")
    val createdAt: String
)
