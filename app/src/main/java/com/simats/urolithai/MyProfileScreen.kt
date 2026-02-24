package com.simats.urolithai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.ui.theme.UroLithAITheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileScreen(navController: NavController) {
    var fullName by remember { mutableStateOf("Rajesh Kumar") }
    var age by remember { mutableStateOf("40") }
    var gender by remember { mutableStateOf("Male") }
    var phone by remember { mutableStateOf("+91 98765 43210") }
    var email by remember { mutableStateOf("rajesh@email.com") }
    var bloodGroup by remember { mutableStateOf("O+") }
    var emergencyContact by remember { mutableStateOf("+91 98765 00000") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Button(
                onClick = { /* Handle save */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
            ) {
                Text("Save Changes", modifier = Modifier.padding(8.dp))
            }
        },
        containerColor = Color(0xFFF8F5FA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileTextField(label = "Full Name", value = fullName, onValueChange = { fullName = it })
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ProfileTextField(label = "Age", value = age, onValueChange = { age = it }, modifier = Modifier.weight(1f))
                ProfileTextField(label = "Gender", value = gender, onValueChange = { gender = it }, modifier = Modifier.weight(1f))
            }
            ProfileTextField(label = "Phone", value = phone, onValueChange = { phone = it })
            ProfileTextField(label = "Email", value = email, onValueChange = { email = it })
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ProfileTextField(label = "Blood Group", value = bloodGroup, onValueChange = { bloodGroup = it }, modifier = Modifier.weight(1f))
                ProfileTextField(label = "Emergency Contact", value = emergencyContact, onValueChange = { emergencyContact = it }, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun ProfileTextField(label: String, value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color(0xFF6A1B9A)
        ),
        shape = RoundedCornerShape(12.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun MyProfileScreenPreview() {
    UroLithAITheme {
        MyProfileScreen(rememberNavController())
    }
}
