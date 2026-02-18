package com.simats.urolithai

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

data class Doctor(
    val name: String,
    val specialization: String,
    val location: String,
    val distance: String,
    val rating: Float,
    val reviewCount: Int,
    val image: Int
)

val doctors = listOf(
    Doctor("Dr. Mahesh Rao", "Urologist", "Indiranagar, Bangalore", "2.5 km", 4.5f, 120, R.drawable.img_4),
    Doctor("Dr. Priya Sharma", "Nephrologist", "Koramangala, Bangalore", "5 km", 4.8f, 250, R.drawable.wrong),
    Doctor("Dr. Anil Kumar", "Urologist", "Jayanagar, Bangalore", "7 km", 4.2f, 98, R.drawable.img_6),
    Doctor("Dr. Sunita Reddy", "Urologist", "Whitefield, Bangalore", "15 km", 4.9f, 450, R.drawable.img_11),
    Doctor("Dr. Rajesh Gupta", "General Physician", "HSR Layout, Bangalore", "10 km", 4.6f, 180, R.drawable.img_8),
    Doctor("Dr. Meena Singh", "Urologist", "Marathahalli, Bangalore", "12 km", 4.4f, 150, R.drawable.img_10)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindDoctorScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Find Doctors") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search doctors") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF6A1B9A),
                        unfocusedBorderColor = Color.LightGray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { /*TODO: Filter action*/ }) {
                    Icon(Icons.Default.FilterList, contentDescription = "Filter")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(id = R.drawable.location), contentDescription = null, tint = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Current Location", fontSize = 12.sp, color = Color.Gray)
                    Text("Bangalore", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.weight(1f))
                TextButton(onClick = { }) {
                    Text("CHANGE", color = Color(0xFF6A1B9A))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(doctors.filter { it.name.contains(searchQuery, ignoreCase = true) || it.specialization.contains(searchQuery, ignoreCase = true) }) { doctor ->
                    DoctorListItem(doctor = doctor)
                }
            }
        }
    }
}

@Composable
fun DoctorListItem(doctor: Doctor) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = doctor.image),
                    contentDescription = doctor.name,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(doctor.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(doctor.specialization, color = Color.Gray, fontSize = 14.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(id = R.drawable.location), contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(doctor.location, color = Color.Gray, fontSize = 12.sp)
                    }
                }
                Text(doctor.distance, color = Color(0xFF6A1B9A), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFFFC107))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${doctor.rating} (${doctor.reviewCount} reviews)", fontWeight = FontWeight.Bold)
                }
                Button(onClick = { /*TODO: Book Appointment*/ }) {
                    Text("Book Appointment")
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
