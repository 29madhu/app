package com.simats.urolithai

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import com.simats.urolithai.network.RetrofitClient
import com.simats.urolithai.network.RegisterRequest
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    onSignupSuccess: () -> Unit,
    onLogin: () -> Unit,
    onTerms: () -> Unit,
    onPolicy: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val apiService = remember { RetrofitClient.getApiService(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Create Account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = errorMessage,
                color = Color.Red
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {

                if (name.isBlank()) {
                    errorMessage = "Enter your name"
                    return@Button
                }

                if (email.isBlank()) {
                    errorMessage = "Enter email"
                    return@Button
                }

                if (password.isBlank()) {
                    errorMessage = "Enter password"
                    return@Button
                }

                if (confirmPassword != password) {
                    errorMessage = "Passwords do not match"
                    return@Button
                }

                isLoading = true
                errorMessage = ""

                scope.launch {

                    try {

                        val response = apiService.register(
                            RegisterRequest(
                                name = name,
                                email = email,
                                password = password,
                                role = "Patient"
                            )
                        )

                        if (response.isSuccessful) {
                            onSignupSuccess()
                        } else {
                            errorMessage = "Signup failed. Please try again."
                        }

                    } catch (e: Exception) {
                        errorMessage = "Server error: ${e.message}"
                    }

                    isLoading = false
                }

            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {

            Text(if (isLoading) "Creating..." else "Create Account")

        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = onLogin) {
            Text("Already have an account? Login")
        }

        Spacer(Modifier.height(8.dp))

        TextButton(onClick = onTerms) {
            Text("Terms & Conditions")
        }

        TextButton(onClick = onPolicy) {
            Text("Privacy Policy")
        }

    }
}