package com.simats.urolithai

import androidx.compose.foundation.background
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

data class ApprovedCase(val id: String, val title: String, val type: String, val date: String, val status: String)

val approvedCases = listOf(
    ApprovedCase("RPT-001", "Ultrasound Left Kidney", "Ultrasound", "12 Jan 2025", "approved"),
    ApprovedCase("RPT-002", "CT Scan Bilateral", "CT Scan", "08 Jan 2025", "approved"),
    ApprovedCase("RPT-004", "Ultrasound Follow-up", "Ultrasound", "15 Jan 2025", "approved")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorApprovedCasesScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(1) }
    val tabs = listOf("Pending", "Approved", "Rejected", "Follow-up")

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
                                "Approved" -> Icon(painterResource(id = R.drawable.img_14), contentDescription = null, modifier = Modifier.size(24.dp))
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
                if (selectedTabIndex == 1) { // Only showing pending cases for now
                    items(approvedCases) { case ->
                        ApprovedCaseItemCard(navController = navController, case = case)
                    }
                }
            }
        }
    }
}

@Composable
fun ApprovedCaseItemCard(navController: NavController, case: ApprovedCase) {
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
                    text = case.status,
                    color = Color(0xFF388E3C),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(color = Color(0xFFE8F5E9), shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(id = R.drawable.reports), contentDescription = null, tint = Color.Gray, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(case.type, color = Color.Gray)
                Spacer(modifier = Modifier.width(16.dp))
                Icon(painterResource(id = R.drawable.timer), contentDescription = null, tint = Color.Gray, modifier = Modifier.size(24.dp))
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

@Preview(showBackground = true)
@Composable
fun DoctorApprovedCasesScreenPreview() {
    UroLithAITheme {
        DoctorApprovedCasesScreen(rememberNavController())
    }
}
