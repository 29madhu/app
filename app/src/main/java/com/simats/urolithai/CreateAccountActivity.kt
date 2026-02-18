package com.simats.urolithai

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.urolithai.ui.theme.UroLithAITheme

class CreateAccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UroLithAITheme {
                CreateAccountScreen(
                    onNavigateBack = { finish() },
                    onVerifyMobile = { role ->
                        val intent = Intent(this, OtpVerificationActivity::class.java)
                        intent.putExtra("role", role)
                        startActivity(intent)
                    },
                    role = intent.getStringExtra("role") ?: "Patient"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen(onNavigateBack: () -> Unit, onVerifyMobile: (String) -> Unit, role: String) {

    var selectedRole by remember { mutableStateOf(role) }
    var fullName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var phoneNumber by remember { mutableStateOf("") }
    var hospitalWorkingAt by remember { mutableStateOf("") }
    var specialization by remember { mutableStateOf("") }
    var yearsOfExperience by remember { mutableStateOf("") }
    var medicalLicenseNumber by remember { mutableStateOf("") }
    var isGenderMenuExpanded by remember { mutableStateOf(false) }
    var isSpecializationMenuExpanded by remember { mutableStateOf(false) }
    val genderOptions = listOf("Male", "Female", "Other")
    val specializationOptions = listOf("Urologist", "Nephrologist", "General Physician")

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color.White)) {
                TopAppBar(
                    title = { Text("Create Account", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        Text("Step 3/3", modifier = Modifier.padding(end = 16.dp), color = Color.Gray)
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
                LinearProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    color = Color(0xFF6A1B9A),
                    trackColor = Color.LightGray.copy(alpha = 0.3f)
                )
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF3E5F5)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color(0xFF6A1B9A)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Create Account", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Join UroLith AI network", fontSize = 16.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(32.dp))

            // Role Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF7F2FA))
                    .padding(4.dp),
            ) {
                RoleToggleButton(text = "Patient", isSelected = selectedRole == "Patient", modifier = Modifier.weight(1f)) { selectedRole = "Patient" }
                RoleToggleButton(text = "Doctor", isSelected = selectedRole == "Doctor", modifier = Modifier.weight(1f)) { selectedRole = "Doctor" }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedRole == "Patient") {
                 OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6A1B9A)))
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("Age") }, modifier = Modifier.weight(1f), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6A1B9A)))
                    ExposedDropdownMenuBox(expanded = isGenderMenuExpanded, onExpandedChange = { isGenderMenuExpanded = it}, modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = gender,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Gender") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isGenderMenuExpanded) },
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6A1B9A))
                        )
                        ExposedDropdownMenu(expanded = isGenderMenuExpanded, onDismissRequest = { isGenderMenuExpanded = false }) {
                            genderOptions.forEach { option ->
                                DropdownMenuItem(text = { Text(option) }, onClick = {
                                    gender = option
                                    isGenderMenuExpanded = false
                                })
                            }
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(alpha = 0.5f))
                        .clickable { /* TODO: Handle image selection */ },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Add Photo", tint = Color.Gray)
                        Text("Add Photo", color = Color.Gray, fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6A1B9A)))
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = hospitalWorkingAt, onValueChange = { hospitalWorkingAt = it }, label = { Text("Hospital Working At") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6A1B9A)))
                Spacer(modifier = Modifier.height(16.dp))
                ExposedDropdownMenuBox(expanded = isSpecializationMenuExpanded, onExpandedChange = { isSpecializationMenuExpanded = it}, modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = specialization,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Specialization") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isSpecializationMenuExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6A1B9A))
                    )
                    ExposedDropdownMenu(expanded = isSpecializationMenuExpanded, onDismissRequest = { isSpecializationMenuExpanded = false }) {
                        specializationOptions.forEach { option ->
                            DropdownMenuItem(text = { Text(option) }, onClick = {
                                specialization = option
                                isSpecializationMenuExpanded = false
                            })
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = yearsOfExperience, onValueChange = { yearsOfExperience = it }, label = { Text("Years of Experience") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6A1B9A)))
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = medicalLicenseNumber, onValueChange = { medicalLicenseNumber = it }, label = { Text("Medical License Number") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6A1B9A)))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = { Text("Phone Number") }, leadingIcon = { Text("+91")}, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF6A1B9A)))

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { onVerifyMobile(selectedRole) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
            ) {
                Text(text = "Verify Mobile", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun RoleToggleButton(text: String, isSelected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color.White else Color.Transparent)
            .border(
                width = if (isSelected) 1.dp else 0.dp,
                color = if (isSelected) Color(0xFFEFE6F6) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = if (isSelected) Color(0xFF6A1B9A) else Color.Gray, fontWeight = if(isSelected) FontWeight.Bold else FontWeight.Normal)
    }
}

@Preview(showBackground = true)
@Composable
fun CreateAccountScreenPreview() {
    UroLithAITheme {
        CreateAccountScreen(onNavigateBack = {}, onVerifyMobile = {}, role = "Doctor")
    }
}
