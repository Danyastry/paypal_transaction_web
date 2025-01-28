package com.dvstry.paypalweb.present

sealed class PayUiState {
    object LoadingToken : PayUiState()
    object Ready : PayUiState()
    object LoadingOrder : PayUiState()
    data class Error(val message: String?) : PayUiState()
    data class Success(val message: String) : PayUiState()

}