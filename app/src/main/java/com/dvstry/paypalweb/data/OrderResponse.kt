package com.dvstry.paypalweb.data

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    val id: String,
    @SerializedName("status")
    val status: String? = null
)