
package com.simats.urolithai

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
fun DoctorRejectedCasesScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(2) }
    val tabs = listOf("Pending", "Approved", "Rejected", "Follow-up")

    val filteredCases = allCases.filter {
        it.status.equals("Rejected", ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Case Management", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF8F5FA))
            )
        },
        bottomBar = { DoctorBottomNavigationBar(navController) },
        containerColor = Color(0xFFF8F5FA)
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = selectedTabIndex == index
                    val selectedColor = Color(0xFF6A1B9A)
                    val unselectedColor = Color.Gray

                    val tabModifier = if (isSelected) {
                        Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(selectedColor)
                            .clickable { selectedTabIndex = index }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    } else {
                        Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { selectedTabIndex = index }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    }

                    Row(
                        modifier = tabModifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val iconModifier = Modifier.size(16.dp)
                        val tint = if (isSelected) Color.White else unselectedColor

                        when (title) {
                            "Pending" -> Icon(painterResource(id = R.drawable.timer), contentDescription = title, modifier = iconModifier, tint = tint)
                            "Approved" -> Icon(painterResource(id = R.drawable.img_15), contentDescription = title, modifier = iconModifier, tint = tint)
                            "Rejected" -> Icon(painterResource(id = R.drawable.wrong), contentDescription = title, modifier = iconModifier, tint = tint)
                            "Follow-up" -> Icon(painterResource(id = R.drawable.circle), contentDescription = title, modifier = iconModifier, tint = tint)
                        }

                        Text(
                            text = title,
                            color = if (isSelected) Color.White else unselectedColor,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {
                items(filteredCases) { case ->
                    RejectedCaseItemCard(navController = navController, case = case)
                }
            }
        }
    }
}

@Composable
fun RejectedCaseItemCard(navController: NavController, case: Case) {
    val statusColor = Color(0xFFD32F2F)
    val statusBackgroundColor = Color(0xFFFFEBEE)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(case.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(case.id, color = Color.Gray, fontSize = 12.sp)
                }
                Text(
                    text = "rejected",
                    color = statusColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .background(color = statusBackgroundColor, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(id = R.drawable.reports),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFF0F0F0), RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(case.type, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text(case.date, color = Color.Gray, fontSize = 12.sp)
                }
            }
            Button(
                onClick = { navController.navigate("rejected_case_reason/${case.id}") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = statusBackgroundColor)
            ) {
                Text("View Reason", color = statusColor, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorRejectedCasesScreenPreview() {
    UroLithAITheme {
        DoctorRejectedCasesScreen(rememberNavController())
    }
}
