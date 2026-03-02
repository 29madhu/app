package com.simats.urolithai

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.ui.theme.UroLithAITheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorAppInfoScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF7F2FA)
                )
            )
        },
        bottomBar = { DoctorBottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F2FA))
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF6A1B9A)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "U",
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("UroLith AI", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("v1.0.2 (Build 2023.01)", fontSize = 14.sp, color = Color.Gray)
            Text("Doctor Portal", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatusChip("Indore-T Test")
                StatusChip("ABD-203")
                StatusChip("HIPAA-aligned")
            }
            Spacer(modifier = Modifier.height(24.dp))
            SystemStatusCard()
            Spacer(modifier = Modifier.weight(1f))
            Text("Made in India 🇮🇳", fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun StatusChip(text: String) {
    Box(
        modifier = Modifier
            .background(Color(0xFFE6F4EA), RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(text, color = Color(0xFF4CAF50), fontSize = 12.sp)
    }
}

@Composable
fun SystemStatusCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("System Status", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            ServiceStatusItem("Auth Service", "Online")
            ServiceStatusItem("AI Engine v2.0", "Online")
            ServiceStatusItem("Chat Server", "Online")
            ServiceStatusItem("Notification Service", "Online")
        }
    }
}

@Composable
fun ServiceStatusItem(serviceName: String, status: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(serviceName, fontSize = 16.sp, color = Color.Gray)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(Color(0xFF4CAF50), CircleShape)
            )
            Text(
                text = status,
                color = Color(0xFF4CAF50),
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorAppInfoScreenPreview() {
    UroLithAITheme {
        DoctorAppInfoScreen(rememberNavController())
    }
}
