package com.spana.banksampahspana.data.model

data class Admin(
    val gender: String,
    val phone: String,
    val nip: String,
    val name: String,
    val email: String,
    var password: String = ""
)
