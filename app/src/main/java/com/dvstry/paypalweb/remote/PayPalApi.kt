package com.dvstry.paypalweb.remote

import com.dvstry.paypalweb.Cmn.CONTENT_TYPE
import com.dvstry.paypalweb.Cmn.CONTENT_TYPE_JSON
import com.dvstry.paypalweb.Cmn.GRANT_TYPE
import com.dvstry.paypalweb.data.CaptureOrderResponse
import com.dvstry.paypalweb.data.OauthTokenResponse
import com.dvstry.paypalweb.data.OrderRequest
import com.dvstry.paypalweb.data.OrderResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface PayPalApi {

    @FormUrlEncoded
    @POST("v1/oauth2/token")
    suspend fun fetchAccessToken(
        @Header("Authorization") authHeader: String,
        @Header("Content-Type") contentType: String = CONTENT_TYPE,
        @Field("grant_type") grantType: String = GRANT_TYPE
    ): OauthTokenResponse

    @POST("v2/checkout/orders")
    suspend fun createOrder(
        @Header("Authorization") bearerToken: String,
        @Header("PayPal-Request-Id") requestId: String,
        @Header("Content-Type") contentType: String = CONTENT_TYPE_JSON,
        @Body orderRequest: OrderRequest
    ): OrderResponse

    @POST("v2/checkout/orders/{orderID}/capture")
    suspend fun captureOrder(
        @Header("Authorization") bearerToken: String,
        @Header("Content-Type") contentType: String = CONTENT_TYPE_JSON,
        @Path("orderID") orderID: String
    ): CaptureOrderResponse

}