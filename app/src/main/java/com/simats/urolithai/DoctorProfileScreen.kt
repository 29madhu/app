package com.simats.urolithai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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

data class Review(
    val name: String,
    val rating: Int,
    val time: String,
    val comment: String
)

val reviews = listOf(
    Review("Rajesh K.", 5, "2 weeks ago", "Excellent doctor. Very thorough in diagnosis and explained everything clearly."),
    Review("Meena S.", 4, "1 month ago", "Good experience. Wait time was bit long but consultation was good."),
    Review("Ramesh P.", 5, "3 months ago", "Highly recommended for kidney stones treatment. I'm very satisfied with the results.")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorProfileScreen(navController: NavController, specialistInitials: String?) {
    val specialist = specialists.find { it.initials == specialistInitials } ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6A1B9A).copy(alpha = 0.1f))
            )
        },
        bottomBar = {
             Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                    Icon(painter = painterResource(id = R.drawable.chat), contentDescription = "Chat", modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Chat")
                }
                OutlinedButton(onClick = { navController.navigate(Screen.BookAppointment.route) }, modifier = Modifier.weight(1f)) {
                    Icon(painter = painterResource(id = R.drawable.book), contentDescription = "Book", modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Book")
                }
                Button(
                    onClick = {
                        navController.navigate(Screen.Home.route)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
                ) {
                    Image(painter = painterResource(id = R.drawable.right), contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Select")
                }
            }
        },
        containerColor = Color(0xFFF8F5FA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            DoctorProfileHeader(specialist, navController)
            DoctorDetails(specialist)
            PatientReviews(reviews)
        }
    }
}

@Composable
fun DoctorProfileHeader(specialist: Specialist, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF6A1B9A).copy(alpha = 0.1f))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFFE1BEE7)),
            contentAlignment = Alignment.Center
        ) {
            Text(specialist.initials, fontWeight = FontWeight.Bold, fontSize = 32.sp, color = Color(0xFF6A1B9A))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(specialist.name, fontWeight = FontWeight.Bold, fontSize = 22.sp)
        Text(specialist.specialization, color = Color.Gray, fontSize = 16.sp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.building), contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(specialist.hospital, color = Color.Gray, fontSize = 14.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("${specialist.experience} years", fontWeight = FontWeight.Bold)
                Text("Experience", color = Color.Gray, fontSize = 12.sp)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("${specialist.rating} ★", fontWeight = FontWeight.Bold)
                Text("Rating", color = Color.Gray, fontSize = 12.sp)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("₹${specialist.fee}", fontWeight = FontWeight.Bold)
                Text("Fee", color = Color.Gray, fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { navController.navigate(Screen.RateExperience.createRoute(specialist.initials)) }) {
            Text("Rate Doctor", color = Color(0xFF6A1B9A))
        }
    }
}

@Composable
fun DoctorDetails(specialist: Specialist) {
    Column(modifier = Modifier.padding(16.dp)) {
        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Row(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.location), contentDescription = null, tint = Color(0xFF6A1B9A))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Location", fontSize = 12.sp, color = Color.Gray)
                        Text(specialist.location, fontWeight = FontWeight.Bold)
                    }
                }
                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.timer), contentDescription = null, tint = Color(0xFF6A1B9A))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text("Availability", fontSize = 12.sp, color = Color.Gray)
                        Text("Mon - Sat", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        AboutSection(specialist)
        Spacer(modifier = Modifier.height(16.dp))
        QualificationsSection(specialist)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AboutSection(specialist: Specialist) {
    Column {
        Text("About", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Dr. ${specialist.name} is a highly experienced ${specialist.specialization} with ${specialist.experience} years of clinical practice. Specializing in urinary stone diagnosis and treatment, including minimally invasive procedures and preventive care.", color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            specialist.tags.forEach { tag ->
                Box(modifier = Modifier.background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Text(tag, fontSize = 12.sp, color = Color.DarkGray)
                }
            }
        }
    }
}

@Composable
fun QualificationsSection(specialist: Specialist) {
    Column {
        Text("Qualifications", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.img_13), contentDescription = null, modifier = Modifier.size(24.dp), tint = Color(0xFF6A1B9A))
            Spacer(modifier = Modifier.width(8.dp))
            Text("MBBS, MD (General Surgery)")
        }
        Spacer(modifier = Modifier.height(4.dp))
         Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.img_13), contentDescription = null, modifier = Modifier.size(24.dp), tint = Color(0xFF6A1B9A))
            Spacer(modifier = Modifier.width(8.dp))
            Text("MCH urology - 1")
        }
        Spacer(modifier = Modifier.height(8.dp))
         Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.right), contentDescription = null, modifier = Modifier.size(24.dp), tint = Color.Green)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Medical Council Verified", fontWeight = FontWeight.Bold)
        }
         Text("License: SAO-URO - 12723", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(start = 32.dp))

    }
}

@Composable
fun PatientReviews(reviews: List<Review>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Patient Reviews", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        reviews.forEach {
            ReviewItem(it)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFE1BEE7)), contentAlignment = Alignment.Center) {
                Text(review.name.first().toString(), color = Color(0xFF6A1B9A), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(review.name, fontWeight = FontWeight.Bold)
                Row {
                    repeat(review.rating) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp))
                    }
                    repeat(5 - review.rating) {
                         Icon(Icons.Default.Star, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(16.dp))
                    }
                }
            }
            Text(review.time, color = Color.Gray, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(review.comment, color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.thumb), contentDescription = "Helpful", modifier = Modifier.size(16.dp), tint = Color.Gray)
            Spacer(modifier = Modifier.width(4.dp))
            Text("Helpful", color = Color.Gray, fontSize = 12.sp)
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorProfileScreenPreview() {
    UroLithAITheme {
        DoctorProfileScreen(rememberNavController(), "RK")
    }
}
