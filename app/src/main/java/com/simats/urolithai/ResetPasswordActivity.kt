package com.simats.urolithai

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.simats.urolithai.ui.theme.UroLithAITheme

class ResetPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UroLithAITheme {
                ForgotPasswordScreen(
                    onNavigateBack = { finish() },
                    onSendOtp = {
                        val intent = Intent(this, VerifyOtpActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}
