package com.simats.urolithai

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.simats.urolithai.ui.theme.UroLithAITheme

class AccountCreatedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val role = intent.getStringExtra("role")
        setContent {
            UroLithAITheme {
                AccountCreatedScreen(role = role) {
                    // This is the onProceed lambda, which should launch the main app
                    val intent = Intent(this, MainActivity::class.java)
                    // Clear the back stack so the user can't navigate back to the auth flow
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
