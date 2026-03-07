package com.simats.urolithai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun DoctorProfileScreen(navController: NavController, doctorName: String?) {
    val doctor = doctorList.find { it.name == doctorName } ?: doctorList[0]

    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { navController.navigate("chat?doctorName=${doctor.name}") }
                    ) {
                        Icon(painter = painterResource(id = R.drawable.think), contentDescription = "Chat", tint = Color(0xFF6A1B9A), modifier = Modifier.size(24.dp))
                        Text("Chat", color = Color(0xFF6A1B9A), fontSize = 12.sp)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { navController.navigate("bookAppointment?doctorName=${doctor.name}") }
                    ) {
                        Icon(painter = painterResource(id = R.drawable.book), contentDescription = "Book", tint = Color.Gray, modifier = Modifier.size(24.dp))
                        Text("Book", color = Color.Gray, fontSize = 12.sp)
                    }
                    Button(
                        onClick = { 
                            navController.previousBackStackEntry?.savedStateHandle?.set("selectedDoctorInitials", doctor.initials)
                            navController.popBackStack("dashboard", false)
                        },
                        modifier = Modifier
                            .height(50.dp)
                            .width(160.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(painter = painterResource(id = R.drawable.thumb), contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Select", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF8F5FA))
        ) {
            ProfileHeaderSection(navController, doctor)
            
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                LocationAndAvailability(doctor)
                AboutSection(doctor)
                QualificationsSection()
                PatientReviewsSection()
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ProfileHeaderSection(navController: NavController, doctor: Doctor) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFF6A1B9A))
        )
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(top = 16.dp, start = 8.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
        }
        
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(doctor.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.think), contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(doctor.specialization, color = Color.Gray, fontSize = 14.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.building), contentDescription = null, modifier = Modifier.size(14.dp), tint = Color.Gray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(doctor.location, color = Color.Gray, fontSize = 14.sp)
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    InfoItem("${doctor.experience} years", "Experience", Color(0xFFE1BEE7), Color(0xFF7B1FA2))
                    InfoItem(doctor.rating.toString(), "Rating", Color(0xFFFFF9C4), Color(0xFFFBC02D), isRating = true)
                    InfoItem("₹${doctor.fee}", "Fee", Color(0xFFC8E6C9), Color(0xFF388E3C))
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = { navController.navigate("rateExperience/${doctor.name}") },
                    modifier = Modifier.fillMaxWidth(0.8f).height(44.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEBE0F5)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Rate Doctor", color = Color(0xFF7B1FA2), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
        
        // Doctor Initials Box
        Box(
            modifier = Modifier
                .padding(top = 35.dp)
                .align(Alignment.TopCenter)
                .size(70.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF7B1FA2))
                .border(4.dp, Color.White, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(doctor.initials, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun InfoItem(value: String, label: String, bgColor: Color, textColor: Color, isRating: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(bgColor, RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(value, color = textColor, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            if (isRating) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.Star, contentDescription = null, tint = textColor, modifier = Modifier.size(14.dp))
            }
        }
        Text(label, color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
fun LocationAndAvailability(doctor: Doctor) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.location), contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFF42A5F5))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Location", fontSize = 12.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(doctor.location, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }
        }
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.timer), contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFF66BB6A))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Availability", fontSize = 12.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Mon - Sat", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Text("09:00 AM - 06:00 PM", fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun AboutSection(doctor: Doctor) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.think), contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF6A1B9A))
                Spacer(modifier = Modifier.width(8.dp))
                Text("About", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "${doctor.name} is a highly experienced urology with ${doctor.experience} years of clinical practice. Specializing in urinary stone diagnosis and treatment, including minimally invasive procedures and preventive care.",
                color = Color.Gray,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TagItem("Kidney Stones")
                TagItem("Urolithology")
            }
        }
    }
}

@Composable
fun TagItem(text: String) {
    Surface(
        color = Color(0xFFF3E5F5),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            fontSize = 11.sp,
            color = Color(0xFF6A1B9A),
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun QualificationsSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.book), contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF6A1B9A))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Qualifications", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            QualificationListItem("MBBS, MS (General Surgery)", "AIIMS New Delhi")
            Spacer(modifier = Modifier.height(12.dp))
            QualificationListItem("M.Ch (Urology)", "PGIMER Chandigarh")
            Spacer(modifier = Modifier.height(12.dp))
            QualificationListItem("Medical Council Verified", "Council ID: URO - 1042")
        }
    }
}

@Composable
fun QualificationListItem(title: String, subtitle: String) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .size(6.dp)
                .clip(CircleShape)
                .background(Color(0xFF6A1B9A))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(subtitle, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun PatientReviewsSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.doctor), contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF6A1B9A))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Patient Reviews", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            ReviewListItem("Rajesh K.", "5.0", "Excellent doctor. Very thorough in diagnosis and explained everything clearly.", "2 hours ago")
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF5F5F5))
            ReviewListItem("Meera G.", "4.5", "Great experience. Wait time was a bit long but the consultation was worth it.", "1 week ago")
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF5F5F5))
            ReviewListItem("Suresh P.", "5.0", "Highly recommended for kidney stone related treatment.", "2 weeks ago")
        }
    }
}

@Composable
fun ReviewListItem(name: String, rating: String, comment: String, time: String) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(time, color = Color.Gray, fontSize = 11.sp)
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
            repeat(5) { index ->
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = if (index < rating.toDouble().toInt()) Color(0xFFFFD600) else Color(0xFFEEEEEE),
                    modifier = Modifier.size(14.dp)
                )
            }
        }
        Text(comment, color = Color.Gray, fontSize = 13.sp, lineHeight = 18.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun DoctorProfileScreenPreview() {
    UroLithAITheme {
        DoctorProfileScreen(rememberNavController(), "Dr. Ravi Kumar")
    }
}
