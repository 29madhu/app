package com.simats.urolithai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun DoctorReviewCaseScreen(navController: NavController, caseId: String?) {
    val case = allCases.find { it.id == caseId } ?: return

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Review Case", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("${case.title}  •  ${case.id}", color = Color.Gray, fontSize = 12.sp)
                        }
                        Row {
                            IconButton(onClick = { /* TODO: Handle chat */ }) {
                                Icon(painterResource(id = R.drawable.think), contentDescription = "Chat", tint = Color(0xFF6A1B9A))
                            }
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Default.Close, contentDescription = "Close")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Black, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(painterResource(id = R.drawable.img_20), contentDescription = "Document", tint = Color.White.copy(alpha = 0.5f), modifier = Modifier.size(64.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("ai_analysis/${case.id}") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
            ) {
                Icon(painterResource(id = R.drawable.img_16), contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Run AI Analysis", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorReviewCaseScreenPreview() {
    UroLithAITheme {
        DoctorReviewCaseScreen(rememberNavController(), "RPT-001")
    }
}
