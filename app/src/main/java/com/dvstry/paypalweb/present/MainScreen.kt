package com.dvstry.paypalweb.present

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(
    uiState: PayUiState,
    onStartOrderClick: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState) {
        if (uiState is PayUiState.Error) {
            snackbarHostState.showSnackbar(uiState.message ?: "Unknown error")
        } else if (uiState is PayUiState.Success) {
            snackbarHostState.showSnackbar(uiState.message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (uiState) {
                    is PayUiState.LoadingToken -> {
                        Text(text = "Fetching Access Token...")
                    }

                    is PayUiState.Ready -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Button(onClick = onStartOrderClick) {
                                Text("Start Order")
                            }
                        }
                    }

                    is PayUiState.LoadingOrder -> {
                        CircularProgressIndicator()
                    }

                    is PayUiState.Error -> {
                        Text(text = "Error: ${uiState.message}")
                    }

                    is PayUiState.Success -> {
                        Text(text = uiState.message)
                    }
                }
            }
        }
    }
}