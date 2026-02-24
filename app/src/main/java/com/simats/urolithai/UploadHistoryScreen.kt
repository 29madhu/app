package com.simats.urolithai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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

data class UploadHistoryItem(
    val title: String,
    val date: String,
    val doctor: String,
    val status: String,
    val icon: Int
)

val uploadHistoryList = listOf(
    UploadHistoryItem(
        title = "Ultrasound Left Kidney",
        date = "12 Jan 2025",
        doctor = "Dr. Priya Sharma",
        status = "pending",
        icon = R.drawable.timer
    ),
    UploadHistoryItem(
        title = "CT Scan Bilateral",
        date = "08 Jan 2025",
        doctor = "Dr. Priya Sharma",
        status = "approved",
        icon = R.drawable.right
    ),
    UploadHistoryItem(
        title = "X-Ray KUB",
        date = "01 Jan 2025",
        doctor = "Dr. Amit Patel",
        status = "rejected",
        icon = R.drawable.wrong
    ),
    UploadHistoryItem(
        title = "Ultrasound Follow-up",
        date = "15 Jan 2025",
        doctor = "Dr. Priya Sharma",
        status = "pending",
        icon = R.drawable.timer
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadHistoryScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Upload History") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF8F5FA)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(uploadHistoryList) { item ->
                UploadHistoryListItem(item = item, navController = navController)
            }
        }
    }
}

@Composable
fun UploadHistoryListItem(item: UploadHistoryItem, navController: NavController) {
    val statusColor = when (item.status) {
        "pending" -> Color(0xFFFFA000)
        "approved" -> Color(0xFF388E3C)
        "rejected" -> Color.Red
        else -> Color.Gray
    }
    val backgroundColor = when (item.status) {
        "pending" -> Color(0xFFFFF3E0)
        "approved" -> Color(0xFFE8F5E9)
        "rejected" -> Color(0xFFFFEBEE)
        else -> Color.LightGray
    }
    val iconBackgroundColor = when (item.status) {
        "pending" -> Color(0xFFFFFBEA)
        "approved" -> Color(0xFFE8F5E9)
        "rejected" -> Color(0xFFFFEBEE)
        else -> Color.LightGray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (item.status == "pending" || item.status == "approved" || item.status == "rejected") {
                    navController.navigate(Screen.ReportDetails.createRoute(item.title))
                }
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = item.icon),
                    contentDescription = item.status,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.title, fontWeight = FontWeight.Bold)
                Text("${item.date} • ${item.doctor}", color = Color.Gray, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = item.status,
                color = statusColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Details",
                tint = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UploadHistoryScreenPreview() {
    UroLithAITheme {
        UploadHistoryScreen(rememberNavController())
    }
}
