package com.dvstry.paypalweb.data

import com.google.gson.annotations.SerializedName

data class ExperienceContext(
    @SerializedName("payment_method_preference")
    val paymentMethodPreference: String,
    @SerializedName("brand_name")
    val brandName: String,
    val locale: String,
    @SerializedName("landing_page")
    val landingPage: String,
    @SerializedName("shipping_preference")
    val shippingPreference: String,
    @SerializedName("user_action")
    val userAction: String,
    @SerializedName("return_url")
    val returnUrl: String,
    @SerializedName("cancel_url")
    val cancelUrl: String
)
