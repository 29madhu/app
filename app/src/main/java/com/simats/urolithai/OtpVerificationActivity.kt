package com.simats.urolithai

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.simats.urolithai.ui.theme.UroLithAITheme

class OtpVerificationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            UroLithAITheme {

                val role = intent.getStringExtra("role") ?: "Patient"
                val email = intent.getStringExtra("email") ?: ""

                OtpVerificationScreen(

                    email = email,

                    onNavigateBack = {
                        finish()
                    },

                    onOtpVerified = { userId ->

                        val intent = Intent(this, AccountCreatedActivity::class.java)
                        intent.putExtra("role", role)
                        intent.putExtra("userId", userId)
                        startActivity(intent)

                    }
                )
            }
        }
    }
}