package com.spana.banksampahspana.data.model

data class User (
    var id: Int = 0,
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var nis: String = "",
    var studentClass: Int = 0,
    var gender: String = "",
    var paymentMethod: String = "",
    var phone: String = ""
)