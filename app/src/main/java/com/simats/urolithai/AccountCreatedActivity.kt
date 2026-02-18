package com.simats.urolithai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.ui.theme.UroLithAITheme

class AccountCreatedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val role = intent.getStringExtra("role")
        setContent {
            UroLithAITheme {
                AccountCreatedScreen(navController = rememberNavController(), role = role)
            }
        }
    }
}
