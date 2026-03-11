package com.simats.urolithai

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.urolithai.network.LoginRequest
import com.simats.urolithai.network.RetrofitClient
import com.simats.urolithai.network.TokenManager
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    onForgotPassword: () -> Unit
) {

    var selectedRole by remember { mutableStateOf("Patient") }
    var userId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val tokenManager = remember { TokenManager(context) }
    val apiService = remember { RetrofitClient.getApiService(context) }

    Scaffold(containerColor = Color.White) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(64.dp))

            Image(
                painter = painterResource(id = R.drawable.welcome),
                contentDescription = "Logo",
                modifier = Modifier.size(80.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                "Welcome Back",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "Secure access to your medical records",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF7F2FA))
                    .padding(16.dp)
            ) {

                Column {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .padding(4.dp)
                    ) {

                        RoleToggleButton(
                            text = "Patient",
                            icon = R.drawable.human,
                            isSelected = selectedRole == "Patient",
                            modifier = Modifier.weight(1f)
                        ) { selectedRole = "Patient" }

                        RoleToggleButton(
                            text = "Doctor",
                            icon = R.drawable.img_16,
                            isSelected = selectedRole == "Doctor",
                            modifier = Modifier.weight(1f)
                        ) { selectedRole = "Doctor" }
                    }

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = userId,
                        onValueChange = { userId = it },
                        label = { Text("Patient / Doctor ID") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = {

                            if (userId.isBlank()) {
                                errorMessage = "Please enter ID"
                                return@Button
                            }

                            if (password.isBlank()) {
                                errorMessage = "Please enter password"
                                return@Button
                            }

                            isLoading = true
                            errorMessage = ""

                            scope.launch {

                                try {

                                    val response =
                                        apiService.login(
                                            LoginRequest(userId, password)
                                        )

                                    if (response.isSuccessful && response.body() != null) {

                                        val auth = response.body()!!

                                        tokenManager.saveTokens(
                                            auth.access,
                                            auth.refresh
                                        )

                                        onLoginSuccess(selectedRole)

                                    } else {

                                        errorMessage = "Invalid ID or Password"

                                    }

                                } catch (e: Exception) {

                                    errorMessage = "Server error"

                                } finally {

                                    isLoading = false

                                }

                            }

                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),

                        shape = RoundedCornerShape(16.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6A1B9A)
                        )

                    ) {

                        Text(if (isLoading) "Logging in..." else "Login")

                        Spacer(Modifier.width(8.dp))

                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null
                        )

                    }

                }

            }

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                TextButton(onClick = onForgotPassword) {
                    Text("Forgot Password?", color = Color.Gray)
                }

                TextButton(onClick = {

                    context.startActivity(
                        Intent(context, CreateAccountActivity::class.java)
                    )

                }) {

                    Row {

                        Text("New User? ", color = Color.Gray)

                        Text(
                            "Register Here",
                            color = Color(0xFF6A1B9A),
                            fontWeight = FontWeight.Bold
                        )

                    }

                }

            }

        }

    }

}

@Composable
fun RoleToggleButton(
    text: String,
    icon: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) Color(0xFFEDE7F6) else Color.Transparent)
            .clickable { onClick() }
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(icon),
            contentDescription = text,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )

    }

}