package com.dvstry.paypalweb.present

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvstry.paypalweb.rep.PayPalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PayPalViewModel @Inject constructor(
    private val repository: PayPalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<PayUiState>(PayUiState.LoadingToken)
    val uiState = _uiState.asStateFlow()

    private var accessToken: String = ""
    private var orderId: String = ""

    fun initPayPal() {
        viewModelScope.launch {
            try {
                _uiState.value = PayUiState.LoadingToken
                accessToken = repository.fetchAccessToken()
                _uiState.value = PayUiState.Ready
            } catch (e: Exception) {
                _uiState.value = PayUiState.Error(e.message)
            }
        }
    }

    fun startOrder(returnUrl: String) {
        viewModelScope.launch {
            try {
                _uiState.value = PayUiState.LoadingOrder
                val uniqueId = UUID.randomUUID().toString()
                orderId = repository.createOrder(
                    accessToken = accessToken,
                    uniqueId = uniqueId,
                    returnUrl = returnUrl
                )
                _uiState.value = PayUiState.Success("Order Created: $orderId")
            } catch (e: Exception) {
                _uiState.value = PayUiState.Error(e.message)

            }
        }
    }

    fun captureOrder() {
        viewModelScope.launch {
            try {
                val status = repository.captureOrder(accessToken, orderId)
                _uiState.value = PayUiState.Success("Capture Status: $status")
            } catch (e: Exception) {
                _uiState.value = PayUiState.Error(e.message)
            }
        }
    }

    fun getCurrentOrderId(): String = orderId


}