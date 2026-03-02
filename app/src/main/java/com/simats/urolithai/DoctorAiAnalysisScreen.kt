package com.simats.urolithai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.ui.theme.UroLithAITheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorAiAnalysisScreen(navController: NavController, caseId: String?) {
    var showRejectionDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Review Case") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painterResource(id = R.drawable.img_14), contentDescription = "Back", modifier = Modifier.size(24.dp))
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Chat */ }) {
                        Icon(Icons.AutoMirrored.Outlined.Chat, contentDescription = "Chat", tint = Color(0xFF6A1B9A))
                    }
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF8F5FA))
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { showRejectionDialog = true },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Icon(painterResource(id = R.drawable.wrong), contentDescription = "Reject", tint = Color.Red, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Reject", color = Color.Red)
                }
                Button(
                    onClick = { navController.navigate("write_prescription") },
                    modifier = Modifier.weight(1f).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(painterResource(id = R.drawable.right), contentDescription = "Approve & Rx", tint = Color.White, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Approve & Rx")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF8F5FA))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AiStoneAnalysisCard()
            Stone3dModelCard()
            StoneIdentificationCard()
            ClinicalRecommendationsCard()
            DoctorNotesCard()
        }
    }

    if (showRejectionDialog) {
        RejectionReasonDialog(
            onConfirm = { 
                showRejectionDialog = false
                navController.popBackStack(navController.graph.startDestinationId, false)
            },
            onDismiss = { showRejectionDialog = false }
        )
    }
}

@Composable
fun RejectionReasonDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    var reason by remember { mutableStateOf("Report not clear") }
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("Rejection Reason", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                OutlinedTextField(
                    value = reason,
                    onValueChange = { reason = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)){
                    OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = onConfirm,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Confirm Reject")
                    }
                }
            }
        }
    }
}

@Composable
fun AiStoneAnalysisCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.img_17), contentDescription = "AI Stone Analysis", tint = Color(0xFF6A1B9A), modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("AI Stone Analysis", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "Processing",
                    color = Color(0xFF6A1B9A),
                    modifier = Modifier
                        .background(Color(0xFFF3E5F5), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            Text("PID: P2024-0021 | Scan ID: IMG-9887", fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.think),
                    contentDescription = "Stone",
                    modifier = Modifier.size(100.dp)
                )
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoRow("Size", "6.2mm")
                    InfoRow("Location", "L. Kidney \n[Lower]")
                    LinearProgressIndicator(
                        progress = { 0.942f },
                        modifier = Modifier.fillMaxWidth().height(8.dp).clip(CircleShape),
                        color = Color(0xFF6A1B9A),
                        trackColor = Color(0xFFF3E5F5)
                    )
                    Text("94.2 %", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.align(Alignment.End))
                }
            }
        }
    }
}

@Composable
fun InfoRow(title: String, value: String) {
    Column {
        Text(title, fontSize = 12.sp, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Stone3dModelCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                 Icon(painter = painterResource(id = R.drawable.img_16), contentDescription = "3D Stone Model", tint = Color(0xFF6A1B9A), modifier = Modifier.size(24.dp))
                 Spacer(modifier = Modifier.width(8.dp))
                 Text("3D Stone Model", fontWeight = FontWeight.Bold)
                 Spacer(modifier = Modifier.weight(1f))
                 TextButton(onClick={}) {
                     Text("Open in Full")
                 }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                 Icon(Icons.Outlined.PlayCircle, contentDescription = "Play", tint = Color.White, modifier = Modifier.size(64.dp))
            }
        }
    }
}

@Composable
fun StoneIdentificationCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.Shield, contentDescription = "Detected Type", modifier = Modifier.size(40.dp), tint = Color(0xFF6A1B9A))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Detected Type", color = Color.Gray, fontSize = 12.sp)
                Text("Calcium Oxalate", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun ClinicalRecommendationsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                 Icon(painter = painterResource(id = R.drawable.danger), contentDescription = "Clinical Recommendations", tint = Color(0xFF6A1B9A), modifier = Modifier.size(28.dp))
                 Spacer(modifier = Modifier.width(8.dp))
                 Text("Clinical Recommendations", fontWeight = FontWeight.Bold)
            }
            Text("• Increase fluid intake to > 2.5L/day")
            Text("• Limit sodium intake.")
            Text("• Reduce animal protein consumption.")
            Text("• Follow up in 3 months with repeat ultrasound.")
        }
    }
}

@Composable
fun DoctorNotesCard() {
    var notes by remember { mutableStateOf("") }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                 Icon(painter = painterResource(id = R.drawable.book), contentDescription = "Doctor Notes", tint = Color(0xFF6A1B9A), modifier = Modifier.size(24.dp))
                 Spacer(modifier = Modifier.width(8.dp))
                 Text("Doctor Notes", fontWeight = FontWeight.Bold)
            }
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Add your clinical observations...") },
                modifier = Modifier.fillMaxWidth().height(100.dp),
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorAiAnalysisScreenPreview() {
    UroLithAITheme {
        DoctorAiAnalysisScreen(rememberNavController(), "RPT-001")
    }
}
