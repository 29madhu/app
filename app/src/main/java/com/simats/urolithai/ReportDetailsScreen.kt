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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportDetailsScreen(navController: NavController, uploadId: String?) {
    val report = uploadHistoryList.find { it.title == uploadId }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Report Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (report?.status == "approved") {
                        IconButton(onClick = { navController.navigate(Screen.ShareReport.route) }) {
                            Icon(painterResource(id = R.drawable.share), contentDescription = "Share", tint = Color(0xFF6A1B9A))
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF8F5FA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (report?.status) {
                "approved" -> {
                    ReportHeaderCardApproved()
                    AiAnalysisResultCard()
                    DoctorsNotesCard()
                    ActionButtons(navController)
                }
                "pending" -> {
                    ReportHeaderCardPending()
                }
                "rejected" -> {
                    ReportHeaderCardRejected(navController, report)
                }
            }
        }
    }
}

@Composable
fun ReportHeaderCardRejected(navController: NavController, report: UploadHistoryItem?) {
    Column {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFCDD2)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.wrong),
                        contentDescription = "Report Rejected",
                        tint = Color.Red,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Report Rejected", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Red)
                    Text(
                        "This report was not utilized for analysis. Please see notes for correction.",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.danger), contentDescription = "Reason for Rejection", tint = Color.Red, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Reason for Rejection", fontWeight = FontWeight.Bold, color = Color.Red)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Image quality too low. Please reupload a clearer scan.", color = Color.Gray)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                    Text(report?.title ?: "", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(
                        "REJECTED",
                        color = Color.Red,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(color = Color(0xFFFFEBEE), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
                Text("ID: RPT-003", color = Color.Gray, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(16.dp))
                DetailRow(icon = R.drawable.timer, label = report?.date ?: "")
                DetailRow(icon = R.drawable.human, label = report?.doctor ?: "")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { 
                val doctor = doctorList.find { it.name == report?.doctor }
                if (doctor != null) {
                    navController.getBackStackEntry(Screen.Home.route).savedStateHandle["selectedDoctorInitials"] = doctor.initials
                }
                navController.popBackStack(Screen.Home.route, false)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.upload), contentDescription = "Upload New Report", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Upload New Report", color = Color.White)
        }
    }
}

@Composable
fun ReportHeaderCardPending() {
    Column {
        ReviewCard()
        Spacer(modifier = Modifier.height(24.dp))
        ReportSummaryCard()
    }
}

@Composable
fun ReportHeaderCardApproved() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Column {
                    Text("CT Scan Bilateral", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Report ID: RPT-002", color = Color.Gray, fontSize = 12.sp)
                }
                Text(
                    "approved",
                    color = Color(0xFF388E3C),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                DetailRow(icon = R.drawable.book, label = "08 Jan 2025")
                DetailRow(icon = R.drawable.human, label = "Dr. Priya Sharma")
            }
        }
    }
}

@Composable
fun AiAnalysisResultCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF6A1B9A))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.reports), contentDescription = "AI Analysis", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("AI Analysis Result", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Stone Type", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                    Text("Calcium\nOxalate", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Size", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                    Text("6.2mm", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                Text("Location", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                Text("Left Kidney Lower Pole", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun DoctorsNotesCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.reports), contentDescription = "Doctor's Notes", tint = Color(0xFF6A1B9A))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Doctor's Notes", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Stone size is moderate. Immediate surgery is not required. Prescribed medication to help dissolve/pass the stone. Increase fluid intake significantly. Follow up in 2 weeks.",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ActionButtons(navController: NavController) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.download), contentDescription = "Download PDF", tint = Color(0xFF6A1B9A))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Download PDF", color = Color(0xFF6A1B9A))
        }
        Button(
            onClick = { navController.navigate(Screen.ShareReport.route) },
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.share), contentDescription = "Share Report", tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Share Report", color = Color.White)
        }
    }
}

@Composable
fun ReviewCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9E6))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFF3C4)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.timer),
                    contentDescription = "Under Review",
                    tint = Color(0xFFFFA000),
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Under Review", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(
                    "Your report is currently being reviewed by Dr. Priya Sharma. You will be notified once the analysis is complete.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun ReportSummaryCard() {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text("Ultrasound Left Kidney", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(
                "PENDING",
                color = Color(0xFFFFA000),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(
                        color = Color(0xFFFFF3E0),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("ID: RPT-001", color = Color.Gray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(16.dp))
        DetailRow(icon = R.drawable.timer, label = "12 Jan 2025")
        Spacer(modifier = Modifier.height(16.dp))
        DetailRow(icon = R.drawable.human, label = "Dr. Priya Sharma")
        Spacer(modifier = Modifier.height(16.dp))
        DetailRow(icon = R.drawable.book, label = "Ultrasound")
    }
}

@Composable
fun DetailRow(icon: Int, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(id = icon), contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(label, color = Color.Gray, fontSize = 14.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun ReportDetailsScreenPreview() {
    UroLithAITheme {
        ReportDetailsScreen(rememberNavController(), "X-Ray KUB")
    }
}
