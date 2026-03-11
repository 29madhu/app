package com.simats.urolithai

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class AccountCreatedActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = intent.getStringExtra("userId") ?: ""

        setContent {

            AccountCreatedScreen(

                userId = userId,

                onCreatePassword = { id ->

                    val intent = Intent(
                        this@AccountCreatedActivity,
                        CreatePasswordActivity::class.java
                    )

                    intent.putExtra("userId", id)

                    startActivity(intent)

                    finish()

                }

            )

        }
    }
}