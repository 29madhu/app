package com.simats.urolithai

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun DoctorApprovedCasesScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableIntStateOf(1) }
    val tabs = listOf("Pending", "Approved", "Rejected", "Follow-up")

    val filteredCases = allCases.filter {
        it.status.equals(tabs[selectedTabIndex], ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Case Management", fontWeight = FontWeight.Bold, fontSize = 22.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = { DoctorBottomNavigationBar(navController) },
        containerColor = Color(0xFFF8F9FA)
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = selectedTabIndex == index
                    val selectedColor = Color(0xFF6A1B9A)
                    val unselectedColor = Color.Gray

                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .clickable { selectedTabIndex = index },
                        color = if (isSelected) selectedColor else Color.White,
                        shape = RoundedCornerShape(24.dp),
                        border = if (isSelected) null else BorderStroke(1.dp, Color(0xFFE0E0E0))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            val tint = if (isSelected) Color.White else unselectedColor
                            val iconModifier = Modifier.size(18.dp)
                            
                            val iconRes = when (title) {
                                "Pending" -> R.drawable.timer
                                "Approved" -> R.drawable.img_15
                                "Rejected" -> R.drawable.img_25
                                "Follow-up" -> R.drawable.img_26
                                else -> R.drawable.timer
                            }
                            Icon(painterResource(id = iconRes), null, modifier = iconModifier, tint = tint)

                            Text(
                                text = title,
                                color = if (isSelected) Color.White else unselectedColor,
                                fontSize = 13.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredCases) { case ->
                    ApprovedCaseItemCard(navController = navController, case = case)
                }
            }
        }
    }
}

@Composable
fun ApprovedCaseItemCard(navController: NavController, case: Case) {
    val statusColor = Color(0xFF388E3C)
    val statusBackgroundColor = Color(0xFFE8F5E9)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(case.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(case.id, color = Color.Gray, fontSize = 12.sp)
                }
                Surface(
                    color = statusBackgroundColor,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = case.status.lowercase(),
                        color = statusColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(44.dp),
                    color = Color(0xFFF5F5F5),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(id = R.drawable.reports),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(case.type, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                    Text(case.date, color = Color.Gray, fontSize = 12.sp)
                }
            }

            Button(
                onClick = { navController.navigate("patient_history") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE8F5E9),
                    contentColor = Color(0xFF388E3C)
                )
            ) {
                Text("View Details", fontWeight = FontWeight.Bold, fontSize = 14.sp)
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
