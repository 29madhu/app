package com.simats.urolithai

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
    val doctor = doctorList.find { it.name == doctorName }
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { navController.navigate(Screen.Chat.createRoute(doctorName ?: "")) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Chat", color = Color(0xFF6A1B9A))
                }
                Button(
                    onClick = { 
                        navController.previousBackStackEntry?.savedStateHandle?.set("selectedDoctorInitials", doctor?.initials)
                        navController.popBackStack(Screen.Home.route, false)
                     },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Book")
                }
                Button(
                    onClick = { navController.navigate(Screen.VoiceCall.createRoute(doctorName ?: "")) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Consult")
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
            if (doctor != null) {
                ProfileHeader(navController, doctor)
            }
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                if (doctor != null) {
                    DoctorInfoCard(doctor)
                }
                AboutCard()
                QualificationsCard()
                PatientReviewsCard()
            }
        }
    }
}

@Composable
fun ProfileHeader(navController: NavController, doctor: Doctor) {
    Box {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color(0xFF6A1B9A))
        )
        Row(modifier = Modifier.padding(16.dp)){
            IconButton(onClick = { navController.popBackStack() }) {
                Image(painter = painterResource(id = R.drawable.img_14), contentDescription = "Back")
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF3E5F5)),
                contentAlignment = Alignment.Center
            ) {
                Text(doctor.initials, fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6A1B9A))
            }
            Text(doctor.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(doctor.specialization, color = Color.Gray)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.location), contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                Text(doctor.location, color = Color.Gray)
            }
        }
    }
}

@Composable
fun DoctorInfoCard(doctor: Doctor) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${doctor.experience} years", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Experience", color = Color.Gray)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
                        Text(doctor.rating.toString(), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                    Text("Rating", color = Color.Gray)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("₹${doctor.fee}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Fee", color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                Text("View All Reviews")
            }
        }
    }
}

@Composable
fun AboutCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("About", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Dr. Ravi Kumar has a high success rate in treatment and has been associated with some of the best hospitals. He has a keen interest in clinical research and has many ongoing research projects.",
                color = Color.Gray
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text("Kidney Stones")
                }
                TextButton(onClick = { /*TODO*/ }) {
                    Text("Laparoscopy")
                }
            }
        }
    }
}

@Composable
fun QualificationsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Qualifications", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            QualificationItem(text = "MBBS, MS(Gen surg), M.ch(Uro)")
            QualificationItem(text = "National Board, NY")
            QualificationItem(text = "Medical Council of India")
        }
    }
}

@Composable
fun QualificationItem(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(painter = painterResource(id = R.drawable.building), contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = Color.Gray)
    }
}

@Composable
fun PatientReviewsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Patient Reviews", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            ReviewItem("Monish S", "Very good doctor, friendly and explains the issue very clearly. Treatment has been effective so far.")
            ReviewItem("Mayuri N", "The experience was good and consultation was very detailed. Doctor listened to all our problems.")
            ReviewItem("Ramduta", "Very polite and explains the issue clearly. The prescribed medicine seems to be working. Waiting for results.")
        }
    }
}

@Composable
fun ReviewItem(name: String, review: String) {
    Row(modifier = Modifier.padding(vertical = 8.dp), verticalAlignment = Alignment.Top) {
        Image(painter = painterResource(id = R.drawable.img_13), contentDescription = name, modifier = Modifier.size(40.dp).clip(CircleShape))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(name, fontWeight = FontWeight.Bold)
            Row {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
                Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
                Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
                Icon(Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
                Icon(Icons.Default.Star, contentDescription = null, tint = Color.Gray)
            }
            Text(review, color = Color.Gray)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DoctorProfileScreenPreview() {
    UroLithAITheme {
        DoctorProfileScreen(rememberNavController(), "Dr. Ravi Kumar")
    }
}
