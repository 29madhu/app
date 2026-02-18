package com.simats.urolithai

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onRegister: () -> Unit, onForgotPassword: () -> Unit, role: String) {
    Text("Login Screen")
}
