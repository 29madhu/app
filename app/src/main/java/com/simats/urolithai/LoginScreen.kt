package com.simats.urolithai

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.urolithai.ui.theme.UroLithAITheme

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onForgotPassword: () -> Unit,
) {
    var selectedRole by remember { mutableStateOf("Patient") }
    var patientId by remember { mutableStateOf("PAT234567") }
    var password by remember { mutableStateOf("gv5f6gybh") }
    val context = LocalContext.current

    Scaffold(containerColor = Color.White) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            Image(
                painter = painterResource(id = R.drawable.welcome),
                contentDescription = "UroLith AI Logo",
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Welcome Back", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Secure access to your medical records", fontSize = 16.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(32.dp))

            Box(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(Color(0xFFF7F2FA)).padding(16.dp)) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .padding(4.dp),
                    ) {
                        RoleToggleButton(text = "Patient", icon = R.drawable.human, isSelected = selectedRole == "Patient", modifier = Modifier.weight(1f)) { selectedRole = "Patient" }
                        RoleToggleButton(text = "Doctor", icon = R.drawable.img_16, isSelected = selectedRole == "Doctor", modifier = Modifier.weight(1f)) { selectedRole = "Doctor" }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = patientId, onValueChange = { patientId = it }, label = { Text("Patient ID") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6A1B9A)))
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth(), visualTransformation = PasswordVisualTransformation(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6A1B9A)))
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onLoginSuccess,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
                    ) {
                        Text(text = "Login", fontSize = 18.sp)
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                TextButton(onClick = onForgotPassword) { Text("Forgot Password?", color = Color.Gray) }
                TextButton(onClick = { context.startActivity(Intent(context, CreateAccountActivity::class.java)) }) { 
                    Row {
                        Text("New User? ", color = Color.Gray)
                        Text("Register Here", color = Color(0xFF6A1B9A), fontWeight = FontWeight.Bold)
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Secure Medical Data Encryption + HIPAA Aligned", fontSize = 12.sp, color = Color.Gray)
                Text("v1.0.2 • MADE IN INDIA", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun RoleToggleButton(text: String, icon: Int, isSelected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color(0xFFF3E5F5) else Color.Transparent)
            .border(
                width = if (isSelected) 1.dp else 0.dp,
                color = if (isSelected) Color(0xFFD1C4E9) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painterResource(id = icon), contentDescription = text, tint = if (isSelected) Color(0xFF6A1B9A) else Color.Gray)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text, color = if (isSelected) Color(0xFF6A1B9A) else Color.Gray, fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    UroLithAITheme {
        LoginScreen(onLoginSuccess = {}, onForgotPassword = {})
    }
}
