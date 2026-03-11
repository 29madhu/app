package com.simats.urolithai

import com.simats.urolithai.models.Doctor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import com.simats.urolithai.ui.theme.UroLithAITheme


val doctorList = listOf(

    Doctor("AP","Dr. Amit Patel","Nephrology","Yashoda Hospital","Hyderabad, Telangana",4.5,9,"DNB",500,listOf("Chronic renal","Hypertension")),
    Doctor("MI","Dr. Meena Iyer","Urology","Apollo Hospital","Bangalore, Karnataka",4.7,12,"MS",800,listOf("Surgery","Stones")),
    Doctor("RG","Dr. Rajesh Gupta","General Surgery","Manipal Hospital","Bangalore, Karnataka",4.2,5,"MS",300,listOf("General","Inquiry")),
    Doctor("SR","Dr. Suresh Reddy","Urology","Yashoda Hospital","Hyderabad, Telangana",4.9,10,"MD",1000,listOf("Laser Surgery","Prostate")),
    Doctor("KR","Dr. Kavitha Reddy","Nephrology","Sunshine Hospital","Hyderabad, Telangana",4.6,12,"DNB",700,listOf("CKD","Transplant")),
    Doctor("PM","Dr. Prasad Murthy","General Surgery","Santosh Hospital","Hyderabad, Telangana",4.3,8,"MD",1500,listOf("Trauma","General")),
    Doctor("SR","Dr. Srinivas Rao","Urology","Global Hospital","Hyderabad, Telangana",4.6,15,"MD",1500,listOf("Robotic Surgery","Oncology")),
    Doctor("PK","Dr. Padma Kumari","Nephrology","Care Hospital","Hyderabad, Telangana",4.5,19,"MD",1100,listOf("Kidney Failure","Dialysis")),
    Doctor("RR","Dr. Ramana Reddy","Urology","Continental Hospital","Hyderabad, Telangana",4.7,16,"MD",1500,listOf("Laparoscopy","Urology")),
    Doctor("PS","Dr. Priya Sharma","Urologist","Manipal Hospital","Bangalore, Karnataka",4.8,12,"MD",1500,listOf("Kidney Stones","UTI")),
    Doctor("AD","Dr. Anjali Deshmukh","Nephrology","Kailash Hospital","Noida, Uttar Pradesh",4.6,19,"CAC",2000,listOf("Kidney Failure","Transplant")),
    Doctor("VS","Dr. Vikram Singh","Urology","SMS Hospital","New Delhi, Delhi",4.5,20,"MS",1200,listOf("Robotic Surgery","DNB")),
    Doctor("KN","Dr. Kavita Nair","General Surgery","Amrita Hospital","Kochi, Kerala",4.2,16,"MS",1500,listOf("Laparoscopy","Hernia")),
    Doctor("RV","Dr. Rahul Verma","Urology","Medanta","Gurugram, Haryana",4.8,16,"MS",2500,listOf("Stone removal","Infection")),
    Doctor("SR","Dr. Sneha Roy","Nephrology","Apollo Gleneagles","Kolkata, West Bengal",4.6,9,"DM",800,listOf("CKD","Dialysis"))

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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },

        containerColor = Color(0xFFF8F5FA)

    ) { paddingValues ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            items(doctorList) { doctor ->

                DoctorListItem(

                    doctor = doctor,

                    onSelectDoctor = {

                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("selectedDoctorInitials", doctor.initials)

                        navController.popBackStack()

                    },

                    onViewProfile = {
                        navController.navigate("doctorProfile/${doctor.name}")
                    }

                )

            }

        }

    }

}


@Composable
fun DoctorListItem(
    doctor: Doctor,
    onSelectDoctor: () -> Unit,
    onViewProfile: () -> Unit
) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )

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

                    Text(
                        doctor.initials,
                        color = Color(0xFF6A1B9A),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                }

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {

                    Text(
                        doctor.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        "${doctor.specialization} - ${doctor.hospital}",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )

                    Text(
                        doctor.location,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )

                }

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        Icons.Filled.Star,
                        "Rating",
                        tint = Color(0xFFFFC700),
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(Modifier.width(4.dp))

                    Text(
                        doctor.rating.toString(),
                        fontWeight = FontWeight.Bold
                    )

                }

            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {

                    Text(
                        "${doctor.experience} years",
                        fontWeight = FontWeight.Bold
                    )

                    Text("Experience", fontSize = 12.sp)

                }

                Column {

                    Text(
                        doctor.degree,
                        fontWeight = FontWeight.Bold
                    )

                    Text("Degree", fontSize = 12.sp)

                }

                Column {

                    Text(
                        "₹${doctor.fee}",
                        fontWeight = FontWeight.Bold
                    )

                    Text("Fee", fontSize = 12.sp)

                }

            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                TextButton(
                    onClick = onSelectDoctor,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Select Doctor")
                }

                Button(
                    onClick = onViewProfile,
                    modifier = Modifier.weight(1f)
                ) {
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

        FindDoctorScreen(
            rememberNavController()
        )

    }

}
