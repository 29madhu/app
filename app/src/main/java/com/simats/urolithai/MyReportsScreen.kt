package com.simats.urolithai

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

data class MyReport(
    val name: String,
    val date: String,
    val doctor: String,
    val status: String,
    val icon: Int,
    val stoneType: String,
    val size: String,
    val riskLevel: String,
    val medications: List<Pair<String, String>>,
    val dietPlan: List<String>
)

val myReports = listOf(
    MyReport(
        name = "CT Scan",
        date = "08 Jan 2025",
        doctor = "Dr. Priya Sharma",
        status = "approved",
        icon = R.drawable.img_15,
        stoneType = "Calcium Oxalate",
        size = "6.2mm",
        riskLevel = "Moderate",
        medications = listOf("Potassium Citrate" to "1000mg TID", "Tamsulosin" to "0.4mg OD"),
        dietPlan = listOf("Increase fluid intake (>2.5L/day)", "Limit sodium intake", "Reduce animal protein")
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyReportsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Reports") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF8F5FA)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(myReports) { report ->
                ReportListItem(report = report, navController = navController)
            }
        }
    }
}

@Composable
fun ReportListItem(report: MyReport, navController: NavController) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE8F5E9)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(painter = painterResource(id = report.icon), contentDescription = null, modifier = Modifier.size(28.dp))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(report.name, fontWeight = FontWeight.Bold)
                    Text("${report.date} • Ref: ${report.doctor}", color = Color.Gray, fontSize = 14.sp)
                }
                Text(
                    text = report.status,
                    color = Color(0xFF388E3C),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(
                            color = Color(0xFFE8F5E9),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Expand", tint = Color.Gray)
            }
            AnimatedVisibility(visible = isExpanded) {
                ReportDetails(report = report, navController = navController)
            }
        }
    }
}

@Composable
fun ReportDetails(report: MyReport, navController: NavController) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        // Stone Type
        Text("Stone Type", color = Color.Gray, fontSize = 12.sp)
        Text(report.stoneType, color = Color(0xFF6A1B9A), fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))

        // Size and Risk
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            InfoChip("Size", report.size, Modifier.weight(1f))
            InfoChip("Risk Level", report.riskLevel, Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Medications
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Link, contentDescription = "Medications", tint = Color.Gray, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Medications", color = Color.Gray, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))
        report.medications.forEach { medication ->
            MedicationItem(name = medication.first, dosage = medication.second)
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Diet Plan
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.food), contentDescription = "Diet Plan", tint = Color.Gray, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Diet Plan", color = Color.Gray, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))
        report.dietPlan.forEach { plan ->
            Text("• $plan", color = Color.Gray, modifier = Modifier.padding(start = 8.dp))
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ActionButton(icon = R.drawable.share, label = "Share", modifier = Modifier.weight(1f)) { navController.navigate(Screen.ShareReport.route) }
            ActionButton(icon = R.drawable.download, label = "PDF", modifier = Modifier.weight(1f)) { /* TODO */ }
            ActionButton(icon = R.drawable.reports, label = "Details", modifier = Modifier.weight(1f)) { /* TODO */ }
        }
    }
}

@Composable
fun InfoChip(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(label, color = Color.Gray, fontSize = 12.sp)
            Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}

@Composable
fun MedicationItem(name: String, dosage: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(name, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            Text(dosage, color = Color.Gray)
        }
    }
}

@Composable
fun ActionButton(icon: Int, label: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(painter = painterResource(id = icon), contentDescription = label, tint = Color(0xFF6A1B9A))
            Text(label, color = Color(0xFF6A1B9A), fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyReportsScreenPreview() {
    UroLithAITheme {
        MyReportsScreen(rememberNavController())
    }
}
