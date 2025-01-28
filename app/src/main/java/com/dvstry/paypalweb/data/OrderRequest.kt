package com.dvstry.paypalweb.data

import com.google.gson.annotations.SerializedName

data class OrderRequest(
    val intent: String,
    @SerializedName("purchase_units")
    val purchaseUnits: List<PurchaseUnit>,
    @SerializedName("payment_source")
    val paymentSource: PaymentSource?
)
