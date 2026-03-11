package com.simats.urolithai

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.urolithai.network.RetrofitClient
import com.simats.urolithai.network.VerifyOtpRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpVerificationScreen(
    email: String,
    onNavigateBack: () -> Unit,
    onOtpVerified: (String) -> Unit
) {

    val otpDigits = remember { mutableStateListOf("", "", "", "", "", "") }
    val focusRequesters = remember { List(6) { FocusRequester() } }

    var ticks by remember { mutableIntStateOf(60) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val apiService = remember { RetrofitClient.getApiService(context) }

    LaunchedEffect(ticks) {
        if (ticks > 0) {
            delay(1000)
            ticks--
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verify Email", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        containerColor = Color(0xFFF8F5FA)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(64.dp))

            Image(
                painter = painterResource(id = R.drawable.img_10),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Verify OTP",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Enter the OTP sent to your email",
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = email,
                color = Color(0xFF6A1B9A),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                otpDigits.forEachIndexed { index, value ->

                    OutlinedTextField(
                        value = value,
                        onValueChange = { newValue ->

                            if (newValue.length <= 1) {

                                otpDigits[index] = newValue

                                if (newValue.isNotEmpty() && index < 5)
                                    focusRequesters[index + 1].requestFocus()

                                if (newValue.isEmpty() && index > 0)
                                    focusRequesters[index - 1].requestFocus()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(focusRequesters[index])
                            .onKeyEvent {

                                if (it.key == Key.Backspace &&
                                    otpDigits[index].isEmpty() &&
                                    index > 0
                                ) {
                                    focusRequesters[index - 1].requestFocus()
                                    true
                                } else false
                            },
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            if (errorMessage.isNotEmpty()) {

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = errorMessage,
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (ticks > 0) {

                Text(
                    "Resend OTP in ${ticks}s",
                    color = Color.Gray
                )

            } else {

                TextButton(onClick = { ticks = 60 }) {

                    Text(
                        "Resend OTP",
                        color = Color(0xFF6A1B9A)
                    )

                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {

                    val otp = otpDigits.joinToString("")

                    if (otp.length != 6) {
                        errorMessage = "Enter valid OTP"
                        return@Button
                    }

                    isLoading = true
                    errorMessage = ""

                    scope.launch {

                        try {

                            val response = apiService.verifyOtp(
                                VerifyOtpRequest(email, otp)
                            )

                            if (response.isSuccessful && response.body() != null) {

                                val userId = response.body()!!.userId

                                onOtpVerified(userId)

                            } else {

                                errorMessage = "Invalid OTP"
                            }

                        } catch (e: Exception) {

                            errorMessage = "Server error"

                        }

                        isLoading = false
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

                Text(if (isLoading) "Verifying..." else "Verify OTP")

            }

        }

    }

}

