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
fun DoctorRejectedCaseReasonScreen(navController: NavController, caseId: String?) {
    val case = allCases.find { it.id == caseId } ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Review Case", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("${case.date}  •  ${case.id}", color = Color.Gray, fontSize = 12.sp)
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Handle chat */ }) {
                        Icon(painterResource(id = R.drawable.think), contentDescription = "Chat", tint = Color(0xFF6A1B9A))
                    }
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Black, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(painterResource(id = R.drawable.img_20), contentDescription = "Document", tint = Color.White.copy(alpha = 0.5f), modifier = Modifier.size(64.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Scan Preview", color = Color.White.copy(alpha = 0.7f))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Rejection Reason", fontWeight = FontWeight.Bold, color = Color(0xFFD32F2F))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Image quality too low. Please resubmit a clearer scan.", color = Color(0xFFD32F2F))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorRejectedCaseReasonScreenPreview() {
    UroLithAITheme {
        DoctorRejectedCaseReasonScreen(rememberNavController(), "RPT-005")
    }
}
