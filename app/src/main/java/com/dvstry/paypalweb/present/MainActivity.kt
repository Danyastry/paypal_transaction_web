package com.dvstry.paypalweb.present

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import com.dvstry.paypalweb.Cmn.CLIENT_ID
import com.dvstry.paypalweb.Cmn.RETURN_URL
import com.paypal.android.corepayments.CoreConfig
import com.paypal.android.corepayments.Environment
import com.paypal.android.corepayments.PayPalSDKError
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutClient
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutFundingSource
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutListener
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutRequest
import com.paypal.android.paypalwebpayments.PayPalWebCheckoutResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val payPalViewModel: PayPalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        payPalViewModel.initPayPal()

        setContent {
            val uiState = payPalViewModel.uiState.collectAsState()

            MainScreen(
                uiState = uiState.value,
                onStartOrderClick = {
                    payPalViewModel.startOrder(RETURN_URL)
                }
            )

            if (uiState.value is PayUiState.Success) {
                val successMsg = (uiState.value as PayUiState.Success).message
                if (successMsg.contains("Order Created")) {
                    val orderId = payPalViewModel.getCurrentOrderId()
                    launchPayPalCheckout(orderId)
                }
            }
        }
    }

    private fun launchPayPalCheckout(orderId: String) {
        val config = CoreConfig(CLIENT_ID, environment = Environment.SANDBOX)
        val payPalWebCheckoutClient = PayPalWebCheckoutClient(this, config, RETURN_URL)

        payPalWebCheckoutClient.listener = object : PayPalWebCheckoutListener {
            override fun onPayPalWebSuccess(result: PayPalWebCheckoutResult) {
                // ..
            }

            override fun onPayPalWebFailure(error: PayPalSDKError) {
                // ..
            }

            override fun onPayPalWebCanceled() {
                // ..
            }
        }

        val payPalWebCheckoutRequest = PayPalWebCheckoutRequest(
            orderId,
            fundingSource = PayPalWebCheckoutFundingSource.PAYPAL
        )
        payPalWebCheckoutClient.start(payPalWebCheckoutRequest)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { uri ->
            when (uri.getQueryParameter("opType")) {
                "payment" -> {
                    payPalViewModel.captureOrder()
                }

                "cancel" -> {
                    Toast.makeText(this, "Payment Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}