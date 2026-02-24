package com.simats.urolithai

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.simats.urolithai.ui.theme.UroLithAITheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UroLithAITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onLoginSuccess = { role ->
                            if (role == "Doctor") {
                                val intent = Intent(this, DoctorActivity::class.java)
                                startActivity(intent)
                            } else {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                            finish()
                        },
                        onForgotPassword = {
                            val intent = Intent(this, ResetPasswordActivity::class.java)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}
