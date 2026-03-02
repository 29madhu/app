package com.simats.urolithai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.urolithai.ui.theme.UroLithAITheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorTermsAndConditionsSheet(onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        containerColor = Color(0xFFF7F2FA),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Terms & Conditions", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                TextButton(onClick = { onDismiss() }) {
                    Text("Close", color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TermItem(
                title = "1. Acceptance of Terms",
                description = "By accessing and using the UroLith AI app, you accept and agree to be bound by the terms and provision of this agreement."
            )
            TermItem(
                title = "2. Medical Disclaimer",
                description = "This app provides AI-based analysis for informational purposes only. It does not replace professional medical advice, diagnosis, or treatment."
            )
            TermItem(
                title = "3. Data Privacy",
                description = "Your medical data is encrypted and stored securely in compliance with Indian IT Act 2000 and SPDI Rules 2011."
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onDismiss() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
            ) {
                Text("I Accept", color = Color.White, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                TextButton(onClick = { /* TODO: Handle logout */ }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Logout", color = Color.Red, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun TermItem(title: String, description: String) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(description, fontSize = 14.sp, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorTermsAndConditionsSheetPreview() {
    UroLithAITheme {
        DoctorTermsAndConditionsSheet(onDismiss = {})
    }
}
