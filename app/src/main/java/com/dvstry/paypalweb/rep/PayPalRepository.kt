package com.dvstry.paypalweb.rep

interface PayPalRepository {

    suspend fun fetchAccessToken(): String
    suspend fun captureOrder(accessToken: String, orderId: String): String
    suspend fun createOrder(
        accessToken: String,
        uniqueId: String,
        returnUrl: String,
        amount: String = "10.00",
        currencyCode: String = "USD"
    ): String

}