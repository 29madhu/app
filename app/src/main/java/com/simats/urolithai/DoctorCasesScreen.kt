package com.simats.urolithai

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun DoctorCasesScreen(navController: NavController, initialTabIndex: Int?) {
    var selectedTabIndex by remember { mutableStateOf(initialTabIndex ?: 0) }
    val tabs = listOf("Pending", "Approved", "Rejected", "Follow-up")

    val pendingCases = listOf(
        Case("RPT-001", "Ultrasound Left Kidney", "Ultrasound", "12 Jan 2025", "pending"),
        Case("RPT-004", "Ultrasound Follow-up", "Ultrasound", "15 Jan 2025", "pending")
    )
    val approvedCases = listOf(
        Case("RPT-001", "Ultrasound Left Kidney", "Ultrasound", "12 Jan 2025", "approved"),
        Case("RPT-002", "CT Scan Bilateral", "CT Scan", "08 Jan 2025", "approved"),
        Case("RPT-003", "Ultrasound Follow-up", "Ultrasound", "15 Jan 2025", "approved")
    )
    val rejectedCases = listOf(
        Case("RPT-003", "X-Ray KUB", "X-Ray", "01 Jan 2025", "rejected")
    )
    val followUpCases = listOf(
        FollowUpCase("PAT-2024-00422", "Rahul Sharma", "Tomorrow", "15 Dec")
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Case Management") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = { DoctorBottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = Color(0xFF6A1B9A)
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) },
                        selectedContentColor = Color(0xFF6A1B9A),
                        unselectedContentColor = Color.Gray,
                        icon = {
                            when (title) {
                                "Pending" -> Icon(painterResource(id = R.drawable.timer), contentDescription = null, modifier = Modifier.size(24.dp))
                                "Approved" -> Icon(painterResource(id = R.drawable.img_15), contentDescription = null, modifier = Modifier.size(24.dp))
                                "Rejected" -> Icon(painterResource(id = R.drawable.wrong), contentDescription = null, modifier = Modifier.size(24.dp))
                                "Follow-up" -> Icon(painterResource(id = R.drawable.circle), contentDescription = null, modifier = Modifier.size(24.dp))
                            }
                        }
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize().background(Color(0xFFF8F5FA)).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (selectedTabIndex) {
                    0 -> items(pendingCases) { case ->
                        CaseItemCard(navController = navController, case = case)
                    }
                    1 -> items(approvedCases) { case ->
                        ApprovedCaseItemCardLocal(navController = navController, case = case)
                    }
                    2 -> items(rejectedCases) { case ->
                        RejectedCaseItemCard(navController = navController, case = case)
                    }
                    3 -> items(followUpCases) { case ->
                        FollowUpCaseItemCard(case = case)
                    }
                }
            }
        }
    }
}

data class Case(val id: String, val title: String, val type: String, val date: String, val status: String)

data class FollowUpCase(val id: String, val name: String, val time: String, val date: String)

@Composable
fun CaseItemCard(navController: NavController, case: Case) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { navController.navigate("review_case/${case.id}") },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Column {
                    Text(case.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    val text = Text(case.id, color = Color.Gray, fontSize = 12.sp)
                }
                Text(
                    case.status,
                    color = Color(0xFFFFA000),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(color = Color(0xFFFFF3E0), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(id = R.drawable.reports), contentDescription = null, tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text(case.type, color = Color.Gray)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(painterResource(id = R.drawable.timer), contentDescription = null, tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text(case.date, color = Color.Gray)
            }
            Button(
                onClick = { navController.navigate("review_case/${case.id}") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
            ) {
                Text("Review Case", modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun ApprovedCaseItemCardLocal(navController: NavController, case: Case) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Column {
                    Text(case.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(case.id, color = Color.Gray, fontSize = 12.sp)
                }
                Text(
                    case.status,
                    color = Color(0xFF388E3C),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(id = R.drawable.reports), contentDescription = null, tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text(case.type, color = Color.Gray)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(painterResource(id = R.drawable.timer), contentDescription = null, tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text(case.date, color = Color.Gray)
            }
            Button(
                onClick = { navController.navigate("patient_history") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8F5E9))
            ) {
                Text("View Details", modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFF388E3C))
            }
        }
    }
}

@Composable
fun RejectedCaseItemCard(navController: NavController, case: Case) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { navController.navigate("ai_analysis/${case.id}") },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Column {
                    Text(case.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(case.id, color = Color.Gray, fontSize = 12.sp)
                }
                Text(
                    case.status,
                    color = Color.Red,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(color = Color(0xFFFFEBEE), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(id = R.drawable.reports), contentDescription = null, tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text(case.type, color = Color.Gray)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(painterResource(id = R.drawable.timer), contentDescription = null, tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text(case.date, color = Color.Gray)
            }
            Button(
                onClick = { navController.navigate("ai_analysis/${case.id}") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE))
            ) {
                Text("View Reason", modifier = Modifier.padding(vertical = 8.dp), color = Color.Red)
            }
        }
    }
}

@Composable
fun FollowUpCaseItemCard(case: FollowUpCase) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                Column {
                    Text(case.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(case.id, color = Color.Gray, fontSize = 12.sp)
                }
                Text(
                    "Follow-up",
                    color = Color(0xFF2979FF),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(color = Color(0xFFE3F2FD), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(id = R.drawable.timer), contentDescription = null, tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Text(case.date, color = Color.Gray)
                Spacer(modifier = Modifier.width(16.dp))
                Text(case.time, color = Color.Gray)
            }
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3E5F5))
            ) {
                Text("Schedule Appointment", modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFF6A1B9A))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorCasesScreenPreview() {
    UroLithAITheme {
        DoctorCasesScreen(rememberNavController(), 0)
    }
}
