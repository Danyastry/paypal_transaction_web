package com.dvstry.paypalweb.data

import com.google.gson.annotations.SerializedName

data class Amount(
    @SerializedName("currency_code")
    val currencyCode: String,
    val value: String
)
