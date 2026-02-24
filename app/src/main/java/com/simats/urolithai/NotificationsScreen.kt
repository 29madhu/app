package com.simats.urolithai

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.ui.theme.UroLithAITheme

data class Notification(val title: String, val time: String, val isNew: Boolean, val icon: Int)

val notifications = listOf(
    Notification("Your prescription from Dr. Priya Sharma", "15 mins ago", true, R.drawable.medicine),
    Notification("Appointment confirmed for Jan 15", "1h ago", false, R.drawable.right),
    Notification("Report analysis is complete - view results", "3h ago", false, R.drawable.reports)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { 
                    Column {
                        Text("Welcome, Rajesh", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("PID: 2024-00147", fontSize = 12.sp, color = Color.Gray)
                    }
                 },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.MyProfile.route) }) {
                        Image(
                            painter = painterResource(id = R.drawable.img_4),
                            contentDescription = "User Profile",
                            modifier = Modifier.size(32.dp).clip(CircleShape)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFF8F5FA))
            )
        },
        containerColor = Color(0xFFF8F5FA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Notifications", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Box(
                            modifier = Modifier.size(24.dp).background(Color.Red, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("1", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    notifications.forEach { notification ->
                        NotificationItem(notification)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    TextButton(onClick = { /*TODO*/ }) {
                        Text("Mark all as read")
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFF3E5F5)),
            contentAlignment = Alignment.Center
        ) {
            Icon(painter = painterResource(id = notification.icon), contentDescription = null, tint = Color(0xFF6A1B9A))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(notification.title, fontWeight = FontWeight.Medium)
            Text(notification.time, color = Color.Gray, fontSize = 12.sp)
        }
        if (notification.isNew) {
            Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Red))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsScreenPreview() {
    UroLithAITheme {
        NotificationsScreen(rememberNavController())
    }
}
