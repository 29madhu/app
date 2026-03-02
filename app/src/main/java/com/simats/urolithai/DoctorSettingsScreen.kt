package com.simats.urolithai

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.ui.theme.UroLithAITheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorSettingsScreen(navController: NavController) {
    var showTermsAndConditions by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Settings") },
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
        ) {
            DoctorInfoCard()
            Spacer(modifier = Modifier.height(16.dp))
            SettingsItem(icon = R.drawable.human, title = "Edit Profile", onClick = { navController.navigate("doctorEditProfile") })
            SettingsItem(icon = R.drawable.reports, title = "Terms & Conditions", onClick = { showTermsAndConditions = true })
            SettingsItem(iconVector = Icons.Outlined.Security, title = "Privacy Policy", onClick = { navController.navigate("doctorPrivacyPolicy") })
            SettingsItem(icon = R.drawable.ques, title = "Help & FAQs", onClick = { navController.navigate("doctorHelpAndFaqs") })
            SettingsItem(iconVector = Icons.Outlined.Info, title = "App Info", onClick = { navController.navigate("doctorAppInfo") })
        }
    }

    if (showTermsAndConditions) {
        DoctorTermsAndConditionsSheet(onDismiss = { showTermsAndConditions = false })
    }
}

@Composable
fun DoctorInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF6A1B9A)),
                contentAlignment = Alignment.Center
            ) {
                Text("PS", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Dr. Priya Sharma", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Apollo Hospitals", fontSize = 14.sp, color = Color.Gray)
                Text("DOC-URO-1042", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun SettingsItem(icon: Int? = null, iconVector: ImageVector? = null, title: String, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(painterResource(id = icon), contentDescription = title, tint = Color.Gray, modifier = Modifier.size(24.dp))
        } else if (iconVector != null) {
            Icon(iconVector, contentDescription = title, tint = Color.Gray, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, modifier = Modifier.weight(1f), fontSize = 16.sp)
        Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorSettingsScreenPreview() {
    UroLithAITheme {
        DoctorSettingsScreen(rememberNavController())
    }
}
