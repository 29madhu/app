package com.simats.urolithai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.ui.theme.UroLithAITheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorEditProfileScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = { DoctorBottomNavigationBar(navController) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            var fullName by remember { mutableStateOf("Dr. Priya Sharma") }
            var hospital by remember { mutableStateOf("Apollo Hospitals") }
            var specialization by remember { mutableStateOf("Urologist") }
            var experience by remember { mutableStateOf("12") }
            var licenseNumber by remember { mutableStateOf("MCI-URO-1042") }
            var phone by remember { mutableStateOf("+91 98765 43210") }
            var email by remember { mutableStateOf("priya.sharma@apollo.com") }

            ProfileTextField(label = "Full Name", value = fullName, onValueChange = { fullName = it })
            ProfileTextField(label = "Hospital", value = hospital, onValueChange = { hospital = it })
            ProfileTextField(label = "Specialization", value = specialization, onValueChange = { specialization = it })
            ProfileTextField(label = "Experience (Years)", value = experience, onValueChange = { experience = it })
            ProfileTextField(label = "License Number", value = licenseNumber, onValueChange = { licenseNumber = it })
            ProfileTextField(label = "Phone", value = phone, onValueChange = { phone = it })
            ProfileTextField(label = "Email", value = email, onValueChange = { email = it })

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
            ) {
                Text("Save Changes", modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun ProfileTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(label, color = Color.Gray)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorEditProfileScreenPreview() {
    UroLithAITheme {
        DoctorEditProfileScreen(rememberNavController())
    }
}
