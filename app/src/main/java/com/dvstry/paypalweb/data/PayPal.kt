package com.dvstry.paypalweb.data

import com.google.gson.annotations.SerializedName

data class PayPal(
    @SerializedName("experience_context")
    val experienceContext: ExperienceContext
)
