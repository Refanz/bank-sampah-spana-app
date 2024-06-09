package com.spana.banksampahspana.util

import android.text.Editable

object Utils {
    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}