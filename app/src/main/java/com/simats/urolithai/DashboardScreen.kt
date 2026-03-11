package com.simats.urolithai

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simats.urolithai.network.PatientDashboardResponse
import com.simats.urolithai.network.ReportSummary
import com.simats.urolithai.network.RetrofitClient
import kotlinx.coroutines.launch

data class Doctor(
    val id: Int,
    val name: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val apiService = remember { RetrofitClient.getApiService(context) }

    val dashboardData = remember { mutableStateOf<PatientDashboardResponse?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val selectedDoctor = remember { mutableStateOf<Doctor?>(null) }

    LaunchedEffect(Unit) {

        scope.launch {

            try {

                val response = apiService.getPatientDashboard()

                if (response.isSuccessful) {
                    dashboardData.value = response.body()
                } else {
                    errorMessage.value = "Failed to load dashboard"
                }

            } catch (e: Exception) {

                errorMessage.value = e.message ?: "Unknown error"

            } finally {

                isLoading.value = false

            }

        }

    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {

                    Column {

                        Text(
                            "Welcome, ${dashboardData.value?.name ?: "User"}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            "PID: ${dashboardData.value?.id ?: "--"}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                    }

                },

                actions = {

                    IconButton(
                        onClick = { navController.navigate("notifications") }
                    ) {

                        Icon(Icons.Default.Notifications, contentDescription = null)

                    }

                }

            )

        },

        containerColor = Color(0xFFF8F5FA)

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)

        ) {

            if (selectedDoctor.value == null) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFFBEA)
                    )
                ) {

                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text("No Doctor Selected", fontWeight = FontWeight.Bold)

                        Spacer(Modifier.weight(1f))

                        Button(
                            onClick = { navController.navigate("findDoctor") }
                        ) {
                            Text("Select")
                        }

                    }

                }

            }

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF6A1B9A), Color(0xFF9575CD))
                        )
                    )
                    .clickable {
                        navController.navigate("uploadReport")
                    }
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        "Upload Scan",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Spacer(Modifier.weight(1f))

                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = null,
                        tint = Color.White
                    )

                }

            }

            Spacer(Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                StatusCard(
                    "Pending",
                    "2",
                    Color(0xFFFF9800),
                    Modifier.weight(1f)
                )

                StatusCard(
                    "Reports",
                    "${dashboardData.value?.totalReports ?: 0}",
                    Color(0xFF4CAF50),
                    Modifier.weight(1f)
                )

            }

            Spacer(Modifier.height(24.dp))

            Text(
                "Recent Reports",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(Modifier.height(12.dp))

            dashboardData.value?.recentReports?.forEach { report: ReportSummary ->

                ReportItem(
                    title = report.stoneType,
                    date = report.uploadDate,
                    status = report.status,
                    navController = navController
                )

            }

            if (isLoading.value) {

                Spacer(Modifier.height(16.dp))

                Text("Loading...")

            }

            errorMessage.value?.let {

                Spacer(Modifier.height(16.dp))

                Text(it, color = Color.Red)

            }

        }

    }

}

@Composable
fun StatusCard(
    title: String,
    value: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(title, color = Color.White)

            Spacer(Modifier.height(8.dp))

            Text(
                value,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

        }

    }

}

@Composable
fun ReportItem(
    title: String,
    date: String,
    status: String,
    navController: NavController
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {

                Text(title, fontWeight = FontWeight.Bold)

                Text(date, fontSize = 12.sp)

            }

            Text(status)

        }

    }

}