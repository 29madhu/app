package com.simats.urolithai

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    var selectedDoctor by remember { mutableStateOf<Specialist?>(null) }
    val navBackStackEntry = navController.currentBackStackEntry
    val selectedDoctorInitials = navBackStackEntry?.savedStateHandle?.get<String>("selectedDoctorInitials")
    val context = LocalContext.current

    LaunchedEffect(selectedDoctorInitials) {
        if (selectedDoctorInitials != null) {
            selectedDoctor = specialists.find { it.initials == selectedDoctorInitials }
            Toast.makeText(context, "Doctor Selected", Toast.LENGTH_SHORT).show()
            navBackStackEntry.savedStateHandle.remove<String>("selectedDoctorInitials")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Welcome, Rajesh", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("PID: 2024-00147", fontSize = 12.sp, color = Color.Gray)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Notifications.route) }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                    Image(
                        painter = painterResource(id = R.drawable.img_4),
                        contentDescription = "User Profile",
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            if (selectedDoctor == null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEA))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.MedicalServices, contentDescription = null, tint = Color(0xFFFBC02D))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("No Doctor Selected", fontWeight = FontWeight.Bold)
                            Text("Select a doctor to upload reports", fontSize = 12.sp, color = Color.Gray)
                        }
                        TextButton(onClick = { navController.navigate(Screen.FindSpecialist.route) }) {
                            Text("Select")
                        }
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.MedicalServices, contentDescription = null, tint = Color(0xFF388E3C))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(selectedDoctor!!.name, fontWeight = FontWeight.Bold)
                            Text(selectedDoctor!!.specialization, fontSize = 12.sp, color = Color.Gray)
                        }
                        TextButton(onClick = { navController.navigate(Screen.FindSpecialist.route) }) {
                            Text("Change")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("upload_report/${selectedDoctor?.name}") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
                enabled = selectedDoctor != null
            ) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.upload), contentDescription = null, modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Upload Scan", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Upload final report for doctor review", fontSize = 12.sp)
                    }
                    Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatusCard("Pending", "2", Color(0xFFFFF3E0), Color(0xFFFFA000), modifier = Modifier.weight(1f))
                StatusCard("Approved", "1", Color(0xFFE8F5E9), Color(0xFF388E3C), modifier = Modifier.weight(1f))
                StatusCard("Next Appt", "15 Aug", Color(0xFFE3F2FD), Color(0xFF1976D2), modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Quick Actions", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                QuickActionCard("Find Doctor", R.drawable.doctor, modifier = Modifier.weight(1f), onClick = { navController.navigate(Screen.FindSpecialist.route) })
                QuickActionCard("Upload History", R.drawable.upload, modifier = Modifier.weight(1f), onClick = { navController.navigate(Screen.UploadHistory.route) })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                QuickActionCard("Diet & Tips", R.drawable.favourite, modifier = Modifier.weight(1f), onClick = { navController.navigate(Screen.DietPrecautions.route) })
                QuickActionCard("Prescriptions", R.drawable.medicine, modifier = Modifier.weight(1f), onClick = { navController.navigate(Screen.MyPrescriptions.route) })
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Recent Reports", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                TextButton(onClick = { }) {
                    Text("View All")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            ReportItem("Ultrasound Left Kidney", "12, Jan, 2024", "pending", R.drawable.timer, navController)
            ReportItem("CT Scan Bilateral", "02, Jan, 2024", "approved", R.drawable.img_7, navController)
            ReportItem("X-Ray KUB", "20, Dec, 2023", "rejected", R.drawable.img_11, navController)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.location), contentDescription = null, tint = Color(0xFF6A1B9A))
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Current Location", fontSize = 12.sp, color = Color.Gray)
                    Text("Indiranagar, Bangalore", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = { }) {
                    Text("CHANGE")
                }
            }
        }
    }
}

@Composable
fun StatusCard(title: String, value: String, backgroundColor: Color, textColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 14.sp, color = Color.Gray)
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = textColor)
        }
    }
}

@Composable
fun QuickActionCard(title: String, iconRes: Int, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF3E5F5)),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = iconRes), contentDescription = null, modifier = Modifier.size(32.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ReportItem(title: String, date: String, status: String, iconRes: Int, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { navController.navigate("report_details/$title") },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = iconRes), contentDescription = null, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold)
                Text(date, fontSize = 12.sp, color = Color.Gray)
            }
            Text(
                text = status,
                color = when (status) {
                    "pending" -> Color(0xFFFFA000)
                    "approved" -> Color(0xFF388E3C)
                    else -> Color.Red
                },
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(
                        color = when (status) {
                            "pending" -> Color(0xFFFFFBEA)
                            "approved" -> Color(0xFFE8F5E9)
                            else -> Color(0xFFFFEBEE)
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
        }
    }
}
