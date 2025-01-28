package com.dvstry.paypalweb.rep

import android.util.Base64
import com.dvstry.paypalweb.data.Amount
import com.dvstry.paypalweb.data.ExperienceContext
import com.dvstry.paypalweb.data.OrderRequest
import com.dvstry.paypalweb.data.PayPal
import com.dvstry.paypalweb.data.PaymentSource
import com.dvstry.paypalweb.data.PurchaseUnit
import com.dvstry.paypalweb.remote.PayPalApi

class PayPalRepositoryImpl(
    private val payPalApi: PayPalApi,
    private val clientId: String,
    private val secretId: String
) : PayPalRepository {

    override suspend fun fetchAccessToken(): String {
        val authString = "$clientId:$secretId"
        val encodeAuthString = Base64.encodeToString(authString.toByteArray(), Base64.NO_WRAP)
        val authHeader = "Basic $encodeAuthString"
        val response = payPalApi.fetchAccessToken(authHeader)

        return response.accessToken
    }

    override suspend fun captureOrder(accessToken: String, orderId: String): String {
        val bearer = "Bearer $accessToken"
        val captureResponse = payPalApi.captureOrder(
            bearerToken = bearer,
            orderID = orderId
        )
        return captureResponse.status
    }

    override suspend fun createOrder(
        accessToken: String,
        uniqueId: String,
        returnUrl: String,
        amount: String,
        currencyCode: String
    ): String {
        val requestBody = OrderRequest(
            intent = "CAPTURE",
            purchaseUnits = listOf(
                PurchaseUnit(
                    referenceId = uniqueId,
                    amount = Amount(currencyCode, amount)
                )
            ),
            paymentSource = PaymentSource(
                paypal = PayPal(
                    experienceContext = ExperienceContext(
                        paymentMethodPreference = "IMMEDIATE_PAYMENT_REQUIRED",
                        brandName = "SH Developer",
                        locale = "en-US",
                        landingPage = "LOGIN",
                        shippingPreference = "NO_SHIPPING",
                        userAction = "PAY_NOW",
                        returnUrl = returnUrl,
                        cancelUrl = "https://example.com/cancelUrl"
                    )
                )
            )
        )

        val bearer = "Bearer $accessToken"
        val createOrderResponse = payPalApi.createOrder(
            bearerToken = bearer,
            requestId = uniqueId,
            orderRequest = requestBody
        )
        return createOrderResponse.id

    }
}