package com.dvstry.paypalweb.data

import com.google.gson.annotations.SerializedName

data class OauthTokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Long,
    @SerializedName("token_type")
    val tokenType: String
)
