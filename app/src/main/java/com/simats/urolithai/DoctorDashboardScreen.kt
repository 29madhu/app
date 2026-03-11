@file:OptIn(ExperimentalMaterial3Api::class)

package com.simats.urolithai

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.network.DoctorDashboardResponse
import com.simats.urolithai.network.RetrofitClient
import kotlinx.coroutines.launch
import com.simats.urolithai.ui.theme.UroLithAITheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DoctorDashboardScreen(navController: NavController) {

    val dashboardData = remember { mutableStateOf<DoctorDashboardResponse?>(null) }
    val isLoading = remember { mutableStateOf(true) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val apiService = remember { RetrofitClient.getApiService(context) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {

                val response = apiService.getDoctorDashboard()

                if (response.isSuccessful) {
                    dashboardData.value = response.body()
                } else {
                    errorMessage.value = "Failed to load dashboard"
                }

            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }

    Scaffold(
        topBar = { DoctorDashboardTopBar(navController) },
        bottomBar = { DoctorBottomNavigationBar(navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            var searchQuery by remember { mutableStateOf("") }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search patients, cases...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            errorMessage.value?.let {
                Text(it, color = Color.Red)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                SummaryCard(
                    "Pending Reviews",
                    "${dashboardData.value?.pendingReviews ?: 0}",
                    R.drawable.timer,
                    Color(0xFFFFA000),
                    Modifier.weight(1f)
                )

                SummaryCard(
                    "Total Patients",
                    "${dashboardData.value?.totalPatients ?: 0}",
                    R.drawable.img_15,
                    Color(0xFF388E3C),
                    Modifier.weight(1f)
                )
            }

            RecentActivitySection()
            QuickActionsSection(navController)

        }
    }
}

@Composable
fun DoctorDashboardTopBar(navController: NavController) {

    TopAppBar(
        title = {
            Column {
                Text("Welcome, Doctor", fontWeight = FontWeight.Bold)
                Text("DOC-ACTIVE", fontSize = 12.sp, color = Color.Gray)
            }
        },
        actions = {

            BadgedBox(
                badge = { Badge { Text("3") } }
            ) {

                IconButton(onClick = {
                    navController.navigate("doctorNotifications")
                }) {

                    Icon(Icons.Default.Notifications, null)

                }

            }

        }
    )
}

@Composable
fun SummaryCard(
    title: String,
    count: String,
    icon: Int,
    color: Color,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Icon(
                painterResource(icon),
                contentDescription = title,
                tint = Color.White
            )

            Spacer(Modifier.height(8.dp))

            Text(
                count,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                title,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f)
            )

        }

    }

}

@Composable
fun RecentActivitySection() {

    Column {

        Text(
            "Recent Activity",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text("New report from Rajesh Kumar")
                Text("Prescription sent to Meera")
                Text("Appointment with Suresh")

            }

        }

    }

}

@Composable
fun QuickActionsSection(navController: NavController) {

    Column {

        Text(
            "Quick Actions",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            QuickActionItem(
                icon = Icons.Outlined.Analytics,
                label = "Analytics"
            )

        }

    }

}

@Composable
fun QuickActionItem(
    icon: ImageVector,
    label: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF3E5F5)),
            contentAlignment = Alignment.Center
        ) {

            Icon(icon, label)

        }

        Spacer(Modifier.height(8.dp))

        Text(label)

    }

}

@Composable
fun DoctorBottomNavigationBar(navController: NavController) {

    val items = listOf("Home", "Cases", "Chat", "Settings")

    NavigationBar {

        items.forEach {

            NavigationBarItem(
                icon = { Icon(Icons.Default.Home, null) },
                label = { Text(it) },
                selected = false,
                onClick = { }
            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun DoctorDashboardPreview() {

    UroLithAITheme {

        DoctorDashboardScreen(
            rememberNavController()
        )

    }

}