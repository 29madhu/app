package com.simats.urolithai

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
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
import androidx.compose.ui.graphics.Brush
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
    var selectedDoctor by remember { mutableStateOf<Doctor?>(null) }
    val navBackStackEntry = navController.currentBackStackEntry
    val selectedDoctorInitials = navBackStackEntry?.savedStateHandle?.get<String>("selectedDoctorInitials")
    val context = LocalContext.current

    LaunchedEffect(selectedDoctorInitials) {
        if (selectedDoctorInitials != null) {
            selectedDoctor = doctorList.find { it.initials == selectedDoctorInitials }
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
                    BadgedBox(badge = { Badge { Text("3") } }) {
                        IconButton(onClick = { navController.navigate("notifications") }) {
                            Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.img_4),
                        contentDescription = "User Profile",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .clickable { navController.navigate("myProfile") }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
        containerColor = Color(0xFFF8F5FA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // No Doctor Selected Card
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
                        Image(painter = painterResource(id = R.drawable.danger), contentDescription = null, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text("No Doctor Selected", fontWeight = FontWeight.Bold)
                            Text("Select a doctor to upload reports", fontSize = 12.sp, color = Color.Gray)
                        }
                        Button(
                            onClick = { navController.navigate("findDoctor") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
                        ) {
                            Text("Select", color = Color.Black, fontSize = 12.sp)
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
                        Image(painter = painterResource(id = R.drawable.doctor), contentDescription = null, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(selectedDoctor!!.name, fontWeight = FontWeight.Bold)
                            Text(selectedDoctor!!.specialization, fontSize = 12.sp, color = Color.Gray)
                        }
                        TextButton(onClick = { navController.navigate("findDoctor") }) {
                            Text("Change")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Upload Scan Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFF6A1B9A), Color(0xFF9575CD))
                        )
                    )
                    .clickable { navController.navigate("uploadReport") }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(painter = painterResource(id = R.drawable.cam), contentDescription = null, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Upload Scan", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("Upload new report for doctor review", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    }
                    Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null, tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Status Row
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                StatusCard("Pending", "2", Color(0xFFFF9800), modifier = Modifier.weight(1f))
                StatusCard("Approved", "1", Color(0xFF4CAF50), modifier = Modifier.weight(1f))
                NextApptCard("Next Appt", "15", "JAN", Color(0xFF2196F3), modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Quick Actions Section
            Text("Quick Actions", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                QuickActionCard("Find Doctor", R.drawable.doctor, "Specialists nearby", modifier = Modifier.weight(1f), onClick = { navController.navigate("findDoctor") })
                QuickActionCard("Upload History", R.drawable.upload, "Track scan status", modifier = Modifier.weight(1f), onClick = { navController.navigate("uploadHistory") })
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                QuickActionCard("Diet & Tips", R.drawable.img_23, "Health guide", modifier = Modifier.weight(1f), onClick = { navController.navigate("dietPrecautions") })
                QuickActionCard("Prescriptions", R.drawable.medicine, "Your medications", modifier = Modifier.weight(1f), onClick = { navController.navigate("myPrescriptions") })
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Recent Reports Section
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Recent Reports", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                TextButton(onClick = { navController.navigate("myReports") }) {
                    Text("View All", color = Color(0xFF6A1B9A))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            ReportItem("Ultrasound Left Kidney", "12, Jan, 2024", "pending", R.drawable.timer, navController)
            ReportItem("CT Scan Bilateral", "02, Jan, 2024", "approved", R.drawable.img_15, navController)
            ReportItem("X-Ray KUB", "20, Dec, 2023", "rejected", R.drawable.wrong, navController)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Location Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(id = R.drawable.location), contentDescription = null, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Current Location", fontSize = 12.sp, color = Color.Gray)
                        Text("Indiranagar, Bangalore", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3E5F5)),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("CHANGE", color = Color(0xFF6A1B9A), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun StatusCard(title: String, value: String, backgroundColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun NextApptCard(title: String, day: String, month: String, backgroundColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(day, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.width(4.dp))
                Text(month, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun QuickActionCard(title: String, iconRes: Int, subtitle: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        when (title) {
                            "Find Doctor" -> Color(0xFFE0F2F1)
                            "Upload History" -> Color(0xFFF3E5F5)
                            "Diet & Tips" -> Color(0xFFFFF3E0)
                            else -> Color(0xFFE8F5E9)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = iconRes), contentDescription = null, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(subtitle, fontSize = 10.sp, color = Color.Gray)
        }
    }
}

@Composable
fun ReportItem(title: String, date: String, status: String, iconRes: Int, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { navController.navigate("reportDetails/$title") },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
             Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF8F5FA)),
                contentAlignment = Alignment.Center
            ) {
                 Image(painter = painterResource(id = iconRes), contentDescription = null, modifier = Modifier.size(20.dp))
             }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(date, fontSize = 12.sp, color = Color.Gray)
            }
            Text(
                text = status,
                color = when (status) {
                    "pending" -> Color(0xFFFFA000)
                    "approved" -> Color(0xFF388E3C)
                    else -> Color.Red
                },
                fontSize = 10.sp,
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
            Icon(Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.LightGray)
        }
    }
}
