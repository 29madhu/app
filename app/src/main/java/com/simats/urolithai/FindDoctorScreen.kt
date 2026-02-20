package com.simats.urolithai

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.ui.theme.UroLithAITheme

data class Doctor(
    val initials: String,
    val name: String,
    val specialization: String,
    val hospital: String,
    val location: String,
    val rating: Double,
    val experience: Int,
    val degree: String,
    val fee: Int,
    val tags: List<String>
)

val doctorList = listOf(
    Doctor("AP", "Dr. Amit Patel", "Nephrology", "Yashoda Hospital", "Hyderabad, Telangana", 4.5, 9, "DNB", 500, listOf("Chronic renal", "Hypertension")),
    Doctor("MI", "Dr. Meena Iyer", "Urology", "Apollo Hospital, Bangalore", "Bangalore, Karnataka", 4.7, 12, "MS", 800, listOf("Surgery", "Stones")),
    Doctor("RG", "Dr. Rajesh Gupta", "General Surgery", "Manipal Hospital, Bangalore", "Bangalore, Karnataka", 4.2, 5, "MS", 300, listOf("General", "Inquiry")),
    Doctor("SR", "Dr. Suresh Reddy", "Urology", "Yashoda Hospital", "Hyderabad, Telangana", 4.9, 10, "MD", 1000, listOf("Laser Surgery", "Prostate")),
    Doctor("KR", "Dr. Kavitha Reddy", "Nephrology", "Sunshine Hospital", "Hyderabad, Telangana", 4.6, 12, "DNB, FASN", 700, listOf("CKD", "Transplant")),
    Doctor("PM", "Dr. Prasad Murthy", "General Surgery", "Santosh Hospital", "Hyderabad, Telangana", 4.3, 8, "MD", 1500, listOf("Trauma", "General")),
    Doctor("SR", "Dr. Srinivas Rao", "Urology", "Global Hospital", "Hyderabad, Telangana", 4.6, 15, "M.D", 1500, listOf("Robotic Surgery", "Oncology")),
    Doctor("PK", "Dr. Padma Kumari", "Nephrology", "Care Hospital, GVK", "Hyderabad, Telangana", 4.5, 19, "M.D", 1100, listOf("Kidney Failure", "Dialysis")),
    Doctor("RR", "Dr. Ramana Reddy", "Urology", "Continental Hospital", "Hyderabad, Telangana", 4.7, 16, "M.D", 1500, listOf("Laparoscopy", "M.D")),
    Doctor("PS", "Dr. Priya Sharma", "Urologist", "Manipal Hospital", "Bangalore, Karnataka", 4.8, 12, "M.D", 1500, listOf("Kidney Stones", "UTI")),
    Doctor("AD", "Dr. Anjali Deshmukh", "Nephrology", "Kailash Hospital", "Noida, Uttar Pradesh", 4.6, 19, "CAC", 2000, listOf("Kidney Failure", "Transplant")),
    Doctor("VS", "Dr. Vikram Singh", "Urology", "SMS Hospital, Delhi", "New Delhi, Delhi", 4.5, 20, "MS", 1200, listOf("Robotic Surgery", "Core-DNB")),
    Doctor("KN", "Dr. Kavita Nair", "General Surgery", "Amrita Hospital", "Kochi, Kerala", 4.2, 16, "M.S", 1500, listOf("Laparoscopy", "Hernia")),
    Doctor("RV", "Dr. Rahul Verma", "Urology", "Medanta The Medicity", "Gurugram, Haryana", 4.8, 16, "MS", 2500, listOf("Stone removal", "Infection")),
    Doctor("SR", "Dr. Sneha Roy", "Nephrology", "Apollo Gleneagles", "Kolkata, West Bengal", 4.6, 9, "DM", 800, listOf("CKD", "Dialysis"))
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindDoctorScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Find a Doctor") },
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
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(0.dp)) } // For padding top
            items(doctorList) { doctor ->
                DoctorListItem(
                    doctor = doctor, 
                    onSelectDoctor = { navController.navigate(Screen.BookAppointment.route) },
                    onViewProfile = { navController.navigate(Screen.DoctorProfile.createRoute(doctor.name)) }
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) } // For padding bottom
        }
    }
}

@Composable
fun DoctorListItem(doctor: Doctor, onSelectDoctor: () -> Unit, onViewProfile: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFEFE6F6)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(doctor.initials, color = Color(0xFF6A1B9A), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(doctor.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("${doctor.specialization} - ${doctor.hospital}", color = Color.Gray, fontSize = 14.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(id = R.drawable.location), contentDescription = "Location", tint = Color.Gray, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(doctor.location, color = Color.Gray, fontSize = 12.sp)
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Filled.Star, contentDescription = "Rating", tint = Color(0xFFFFC700), modifier = Modifier.size(16.dp))
                    Text(text = doctor.rating.toString(), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
                doctor.tags.forEach { tag ->
                    Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Color(0xFFF3E5F5)).padding(horizontal = 12.dp, vertical = 6.dp)){
                        Text(text = tag, color = Color(0xFF6A1B9A), fontSize = 12.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text("${doctor.experience} years", textAlign = TextAlign.Center, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("Experience", textAlign = TextAlign.Center, fontSize = 12.sp, color = Color.Gray)
                }
                 Column {
                    Text(doctor.degree, textAlign = TextAlign.Center, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("Degree", textAlign = TextAlign.Center, fontSize = 12.sp, color = Color.Gray)
                }
                Column {
                    Text("₹${doctor.fee}", textAlign = TextAlign.Center, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                    Text("Fee", textAlign = TextAlign.Center, fontSize = 12.sp, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
             Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                TextButton(onClick = onSelectDoctor, modifier = Modifier.weight(1f)) {
                    Icon(painterResource(id = R.drawable.thumb), contentDescription = null, tint = Color(0xFF6A1B9A))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Select Doctor", color = Color(0xFF6A1B9A))
                }
                Button(onClick = onViewProfile, modifier = Modifier.weight(1f)) {
                    Text("View Profile")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FindDoctorScreenPreview() {
    UroLithAITheme {
        FindDoctorScreen(rememberNavController())
    }
}
