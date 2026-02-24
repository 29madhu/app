package com.simats.urolithai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.ui.theme.UroLithAITheme

@Composable
fun TermsAndConditionsScreen(navController: NavController) {
    Scaffold(
        containerColor = Color.Black.copy(alpha = 0.6f)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Terms & Conditions", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        TextButton(onClick = { navController.popBackStack() }) {
                            Text("Close", color = Color.Gray)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    TermItem(
                        number = "1.",
                        title = "Acceptance of Terms",
                        content = "By accessing and using the UroLith AI app, you accept and agree to be bound by the terms and provision of this agreement."
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TermItem(
                        number = "2.",
                        title = "Medical Disclaimer",
                        content = "This app provides AI-based analysis for informational purposes only. It does not replace professional medical advice, diagnosis, or treatment."
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TermItem(
                        number = "3.",
                        title = "Data Privacy",
                        content = "Your medical data is encrypted and stored securely in compliance with Indian IT Act 2000 and SPDI Rules 2011."
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("I Accept", modifier = Modifier.padding(8.dp))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    TextButton(
                        onClick = { /* Handle logout */ },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout",
                            tint = Color.Red
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Logout", color = Color.Red)
                    }
                }
            }
        }
    }
}

@Composable
fun TermItem(number: String, title: String, content: String) {
    Row {
        Text(number, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(title, fontWeight = FontWeight.Bold)
            Text(content, color = Color.Gray, fontSize = 14.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TermsAndConditionsScreenPreview() {
    UroLithAITheme {
        TermsAndConditionsScreen(rememberNavController())
    }
}
