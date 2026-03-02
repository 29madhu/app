
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.ui.theme.UroLithAITheme

data class DoctorNotification(val title: String, val subtitle: String, val time: String, val icon: Int, val isNew: Boolean)

val doctorNotifications = listOf(
    DoctorNotification("New Report Uploaded", "Rajesh Kumar uploaded a new Ultrasound scan.", "10 min ago", R.drawable.reports, true),
    DoctorNotification("Appointment Confirmed", "Video consultation with Anita Desai for 4:00 PM", "1 hour ago", R.drawable.book, true),
    DoctorNotification("New Message", "Patient: Dinesh Patel sent a query about medication.", "3 hours ago", R.drawable.think, false),
    DoctorNotification("System Update", "AI Analysis engine updated to v2.1 with higher accuracy.", "Yesterday", R.drawable.img_15, false)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorNotificationsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Notifications") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFF8F5FA))
            )
        },
        containerColor = Color(0xFFF8F5FA)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(doctorNotifications) { notification ->
                DoctorNotificationItem(notification)
            }
        }
    }
}

@Composable
fun DoctorNotificationItem(notification: DoctorNotification) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = notification.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(notification.title, fontWeight = FontWeight.Bold)
                Text(notification.subtitle, color = Color.Gray, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(notification.time, color = Color.Gray, fontSize = 12.sp)
                if (notification.isNew) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Red))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorNotificationsScreenPreview() {
    UroLithAITheme {
        DoctorNotificationsScreen(rememberNavController())
    }
}
