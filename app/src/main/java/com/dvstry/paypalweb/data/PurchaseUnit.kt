package com.dvstry.paypalweb.data

import com.google.gson.annotations.SerializedName

data class PurchaseUnit(
    @SerializedName("reference_id")
    val referenceId: String,
    val amount: Amount
)
