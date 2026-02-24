package com.simats.urolithai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun PreviewPrescriptionScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Preview Prescription") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { navController.popBackStack() }) {
                    Icon(painter = painterResource(id = R.drawable.pen), contentDescription = "Edit", tint = Color(0xFF6A1B9A))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Edit", color = Color(0xFF6A1B9A))
                }
                Button(
                    onClick = { navController.navigate("prescription_sent") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.send), contentDescription = "Send to Patient", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Send to Patient")
                }
            }
        },
        containerColor = Color(0xFFF8F5FA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PrescriptionHeader()
            PatientDetailsCard()
            MedicationsCard()
            AdviceCard()
            DoctorSignature()
        }
    }
}

@Composable
fun PrescriptionHeader() {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween) {
        Column {
            Text("Apollo Hospitals", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF6A1B9A))
            Text("Greams Road, Chennai", color = Color.Gray)
            Text("+91 44 2829 3333", color = Color.Gray)
        }
        Icon(painter = painterResource(id = R.drawable.reports), contentDescription = "Copy", tint = Color.Gray, modifier = Modifier.size(32.dp))
    }
}

@Composable
fun PatientDetailsCard() {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5).copy(alpha = 0.5f))) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            PatientDetail(label = "Name", value = "Rajesh Kumar")
            PatientDetail(label = "Age/Sex", value = "45/M")
            PatientDetail(label = "Date", value = "15 Jan 2025")
            PatientDetail(label = "ID", value = "PAT-00647")
        }
    }
}

@Composable
fun PatientDetail(label: String, value: String) {
    Column {
        Text(label, color = Color.Gray, fontSize = 12.sp)
        Text(value, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun MedicationsCard() {
    Column {
        Text("Rx", fontWeight = FontWeight.Bold, color = Color(0xFF6A1B9A))
        Text("Medications", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Medicine", color = Color.Gray)
                    Text("Dose", color = Color.Gray)
                    Text("Freq", color = Color.Gray)
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                MedicationRow("Potassium Citrate", "1080mg", "1-1-1")
                MedicationRow("Tamsulosin", "0.4mg", "0-0-1")
            }
        }
    }
}

@Composable
fun MedicationRow(name: String, dose: String, freq: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(name)
        Text(dose)
        Text(freq)
    }
}

@Composable
fun AdviceCard() {
    Column {
        Text("Advice", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF6A1B9A))
        Spacer(modifier = Modifier.height(8.dp))
        Text("• Increase fluid intake to 3 liters per day")
        Text("• Low sodium diet")
        Text("• Follow up after 2 weeks")
    }
}

@Composable
fun DoctorSignature() {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 24.dp), horizontalAlignment = Alignment.End) {
        Text("Dr. Priya", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF6A1B9A))
        Text("Dr. Priya Sharma", color = Color.Gray)
        Text("Reg. MCI-URO-1047", color = Color.Gray, fontSize = 12.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewPrescriptionScreenPreview() {
    UroLithAITheme {
        PreviewPrescriptionScreen(rememberNavController())
    }
}
