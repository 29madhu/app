package com.simats.urolithai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
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

data class Medication(val name: String, val dosage: String, val frequency: String, val duration: String)
data class DietaryAdvice(val advice: String)
data class Prescription(
    val doctorName: String,
    val date: String,
    val status: String,
    val medications: List<Medication>,
    val dietaryAdvice: List<DietaryAdvice>,
    val doctorsNotes: String,
    val icon: Int
)

val prescriptions = listOf(
    Prescription(
        doctorName = "Dr. Priya Sharma",
        date = "06 Jan 2025",
        status = "Active",
        medications = listOf(
            Medication("Potassium Citrate", "108mg", "TID (3x daily)", "30 days"),
            Medication("Tamsulosin", "0.4mg", "OD (1x daily)", "14 days")
        ),
        dietaryAdvice = listOf(
            DietaryAdvice("Increase fluid intake > 3L/day"),
            DietaryAdvice("Limit sodium intake to < 2300mg"),
            DietaryAdvice("Reduce animal protein consumption")
        ),
        doctorsNotes = "Follow up after 2 weeks with repeat KFT.",
        icon = R.drawable.timer
    ),
    Prescription(
        doctorName = "Dr. Amit Patel",
        date = "15 Nov 2024",
        status = "Completed",
        medications = emptyList(),
        dietaryAdvice = emptyList(),
        doctorsNotes = "",
        icon = R.drawable.reports
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPrescriptionsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Prescriptions") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
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
            items(prescriptions) { prescription ->
                PrescriptionItem(prescription = prescription)
            }
        }
    }
}

@Composable
fun PrescriptionItem(prescription: Prescription) {
    var isExpanded by remember { mutableStateOf(prescription.status == "Active") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { isExpanded = !isExpanded }) {
                Image(painter = painterResource(id = prescription.icon), contentDescription = null, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(prescription.doctorName, fontWeight = FontWeight.Bold)
                    Text(prescription.date, color = Color.Gray, fontSize = 12.sp)
                }
                Text(
                    prescription.status,
                    color = if (prescription.status == "Active") Color(0xFF6A1B9A) else Color.Gray,
                    modifier = Modifier
                        .background(
                            if (prescription.status == "Active") Color(0xFFF3E5F5) else Color(0xFFF5F5F5),
                            RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Expand")
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(16.dp))
                if(prescription.medications.isNotEmpty()) {
                    Text("Medications", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                    prescription.medications.forEach { medication ->
                        MedicationItem(medication)
                    }
                }
                if (prescription.dietaryAdvice.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Dietary Advice", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                    prescription.dietaryAdvice.forEach { advice ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(4.dp).background(Color.Green, RoundedCornerShape(2.dp)))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(advice.advice)
                        }
                    }
                }
                if (prescription.doctorsNotes.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Doctor's Notes", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                    Text(prescription.doctorsNotes, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = { /* Handle PDF Download */ }) {
                    Icon(painter = painterResource(id = R.drawable.medicine), contentDescription = "Download PDF")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Download PDF")
                }

            }
        }
    }
}

@Composable
fun MedicationItem(medication: Medication) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F5FA))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.medicine), contentDescription = null, tint = Color(0xFF6A1B9A))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(medication.name, fontWeight = FontWeight.Bold)
                Text(medication.frequency, color = Color.Gray, fontSize = 12.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(medication.dosage, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text(medication.duration, color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPrescriptionsScreenPreview() {
    UroLithAITheme {
        MyPrescriptionsScreen(rememberNavController())
    }
}
