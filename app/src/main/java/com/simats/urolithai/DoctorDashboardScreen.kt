package com.simats.urolithai

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
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.ui.theme.UroLithAITheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDashboardScreen(navController: NavController) {
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
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                SummaryCard("Pending Uploads", "3", R.drawable.timer, Color(0xFFFFA000), modifier = Modifier.weight(1f).clickable { navController.navigate("cases/Pending") })
                SummaryCard("Approved Reports", "128", R.drawable.img_15, Color(0xFF388E3C), modifier = Modifier.weight(1f).clickable { navController.navigate("cases/Approved") })
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                SummaryCard("Rejected Cases", "12", R.drawable.wrong, Color(0xFFD32F2F), modifier = Modifier.weight(1f).clickable { navController.navigate("cases/Rejected") })
                SummaryCard("Follow-ups Today", "5", R.drawable.book, Color(0xFF2979FF), modifier = Modifier.weight(1f))
            }

            RecentActivitySection()
            QuickActionsSection(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorDashboardTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Column {
                Text("Welcome, Dr. Priya", fontWeight = FontWeight.Bold)
                Text("DOC-URO-1042", fontSize = 12.sp, color = Color.Gray)
            }
        },
        actions = {
            BadgedBox(badge = { Badge { Text("3") } }) {
                IconButton(onClick = { navController.navigate("doctorNotifications") }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifications")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun SummaryCard(title: String, count: String, icon: Int, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(painterResource(id = icon), contentDescription = title, tint = Color.White, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(count, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.White)
            Text(title, fontSize = 14.sp, color = Color.White.copy(alpha = 0.8f))
        }
    }
}

@Composable
fun RecentActivitySection() {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Recent Activity", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            TextButton(onClick = { /*TODO*/ }) {
                Text("View All")
            }
        }
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                RecentActivityItem(icon = R.drawable.timer, title = "New report from Rajesh Kumar", time = "10 min ago")
                RecentActivityItem(icon = R.drawable.img_15, title = "Prescription sent to Meera", time = "1 hour ago")
                RecentActivityItem(icon = R.drawable.book, title = "Appointment with Suresh", time = "2 hours ago")
            }
        }
    }
}

@Composable
fun RecentActivityItem(icon: Int, title: String, time: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(painterResource(id = icon), contentDescription = null, modifier = Modifier.size(24.dp), tint = Color(0xFF6A1B9A))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(title, fontWeight = FontWeight.Medium)
            Text(time, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Composable
fun QuickActionsSection(navController: NavController) {
    Column {
        Text("Quick Actions", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            QuickActionItem(icon = R.drawable.img_16, label = "AI Analysis", onClick = { navController.navigate("cases/Pending") })
            QuickActionItem(icon = R.drawable.medicine, label = "Write Rx", onClick = { navController.navigate("write_prescription") })
            QuickActionItem(icon = R.drawable.reports, label = "History", onClick = { navController.navigate("cases/Approved") })
            QuickActionItem(icon = Icons.Outlined.Analytics, label = "Analytics", onClick = { navController.navigate("analytics") })
        }
    }
}

@Composable
fun QuickActionItem(icon: Any, label: String, onClick: () -> Unit = {}) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable(onClick = onClick)) {
        Box(modifier = Modifier.size(64.dp).clip(RoundedCornerShape(16.dp)).background(Color(0xFFF3E5F5)), contentAlignment = Alignment.Center) {
             when (icon) {
                is Int -> Icon(painterResource(id = icon), contentDescription = label, modifier = Modifier.size(32.dp), tint = Color(0xFF6A1B9A))
                is ImageVector -> Icon(icon, contentDescription = label, modifier = Modifier.size(32.dp), tint = Color(0xFF6A1B9A))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, fontWeight = FontWeight.Medium)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorBottomNavigationBar(navController: NavController) {
    val items = listOf("Home", "Cases", "Appts", "Chat", "Settings", "Logout")
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = Color.White) {
        items.forEach { screen ->
            val route = when (screen) {
                "Home" -> "dashboard"
                "Cases" -> "cases/Approved"
                "Appts" -> "appointments"
                "Chat" -> "messages"
                "Settings" -> "settings"
                "Logout" -> "logout"
                else -> "dashboard"
            }
            val isSelected = when (screen) {
                "Cases" -> currentRoute?.startsWith("cases") == true
                else -> currentRoute == route
            }
            NavigationBarItem(
                icon = {
                    BadgedBox(badge = {
                        if (screen == "Cases" && !isSelected) { Badge { Text("3") } }
                        if (screen == "Chat" && !isSelected) { Badge { Text("2") } }
                    }) {
                        when (screen) {
                            "Home" -> Icon(Icons.Outlined.Home, contentDescription = screen)
                            "Cases" -> Icon(painterResource(id = R.drawable.reports), contentDescription = screen)
                            "Appts" -> Icon(painterResource(id = R.drawable.book), contentDescription = screen)
                            "Chat" -> Icon(Icons.AutoMirrored.Outlined.Chat, contentDescription = screen)
                            "Settings" -> Icon(Icons.Outlined.Settings, contentDescription = screen)
                            "Logout" -> Icon(Icons.AutoMirrored.Outlined.Logout, contentDescription = screen)
                        }
                    }
                },
                label = { Text(screen) },
                selected = isSelected,
                onClick = { 
                    if (!isSelected) {
                        if (screen == "Logout") {
                            navController.navigate("logout")
                        } else {
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFFF3E5F5),
                    selectedIconColor = Color(0xFF6A1B9A),
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color(0xFF6A1B9A),
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorDashboardScreenPreview() {
    UroLithAITheme {
        DoctorDashboardScreen(rememberNavController())
    }
}
