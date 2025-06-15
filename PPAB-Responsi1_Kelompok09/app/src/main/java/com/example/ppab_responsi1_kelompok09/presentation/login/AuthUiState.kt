package com.example.ppab_responsi1_kelompok09.presentation.login

sealed class AuthUiState {
    object Authenticated : AuthUiState()
    object Unauthenticated : AuthUiState()
    object Loading : AuthUiState()
    data class Error(val message : String) : AuthUiState()
}