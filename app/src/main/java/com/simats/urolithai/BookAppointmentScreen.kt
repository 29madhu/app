package com.simats.urolithai

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.delay
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointmentScreen(navController: NavController) {
    var selectedDate by remember { mutableStateOf(1) }
    var selectedTime by remember { mutableStateOf<String?>(null) }
    var selectedConsultation by remember { mutableStateOf("Voice Call") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Book Appointment") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.VoiceCall.createRoute("Dr. Priya Sharma")) }) {
                        Icon(painterResource(id = R.drawable.call), contentDescription = "Call")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Button(
                onClick = { showSuccessDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Confirm Appointment", modifier = Modifier.padding(8.dp))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DoctorAppointmentCard()
            ConsultationType(selectedConsultation) { selectedConsultation = it }
            DateSelection(selectedDate) { selectedDate = it }
            TimeSelection(selectedTime) { selectedTime = it }
        }
        if (showSuccessDialog) {
            AppointmentSuccessDialog(navController = navController)
        }
    }
}

@Composable
fun AppointmentSuccessDialog(navController: NavController) {
    Dialog(onDismissRequest = { }) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE8F5E9)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.right),
                        contentDescription = "Success",
                        tint = Color(0xFF388E3C),
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Booked Successfully!", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text("Appointment with ")
                    Text("Dr. Priya Sharma", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5))
                ) {
                    Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                        Icon(painter = painterResource(id = R.drawable.book), contentDescription = null, tint = Color(0xFF6A1B9A))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Jan 15, 10:30 AM", color = Color(0xFF6A1B9A), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(Screen.Home.route) {
            popUpTo(Screen.Home.route) { inclusive = true }
        }
    }
}

@Composable
fun DoctorAppointmentCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF3E5F5)),
                contentAlignment = Alignment.Center
            ) {
                Text("PS", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color(0xFF6A1B9A))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Dr. Priya Sharma", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(id = R.drawable.doctor), contentDescription = "Specialization", tint = Color.Gray, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("UROLOGIST • Kushaiguda", color = Color.Gray)
                }
                Text("₹500 Consultation Fee", fontWeight = FontWeight.Bold, color = Color(0xFF6A1B9A))
            }
        }
    }
}

@Composable
fun ConsultationType(selected: String, onSelect: (String) -> Unit) {
    Column {
        Text("Consultation Type", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ConsultationOption(
                text = "Voice Call",
                icon = R.drawable.call,
                isSelected = selected == "Voice Call",
                modifier = Modifier.weight(1f),
                onClick = { onSelect("Voice Call") }
            )
            ConsultationOption(
                text = "Clinic Visit",
                icon = R.drawable.location,
                isSelected = selected == "Clinic Visit",
                modifier = Modifier.weight(1f),
                onClick = { onSelect("Clinic Visit") }
            )
        }
    }
}

@Composable
fun ConsultationOption(text: String, icon: Int, isSelected: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFFF3E5F5) else Color.White),
        border = CardDefaults.outlinedCardBorder(enabled = isSelected)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = text, tint = if (isSelected) Color(0xFF6A1B9A) else Color.Gray, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text, fontWeight = FontWeight.Bold, color = if (isSelected) Color(0xFF6A1B9A) else Color.Black)
        }
    }
}

@Composable
fun DateSelection(selectedDate: Int, onDateSelected: (Int) -> Unit) {
    val calendar = Calendar.getInstance()
    val dates = (0..4).map {
        calendar.add(Calendar.DAY_OF_YEAR, if (it == 0) 0 else 1)
        calendar.clone() as Calendar
    }

    Column {
        Text("Select Date", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            dates.forEachIndexed { index, date ->
                DateBox(date = date, isSelected = selectedDate == index) { onDateSelected(index) }
            }
        }
    }
}

@Composable
fun DateBox(date: Calendar, isSelected: Boolean, onDateSelected: () -> Unit) {
    val dayOfWeek = arrayOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT")[date.get(Calendar.DAY_OF_WEEK) - 1]
    val dayOfMonth = date.get(Calendar.DAY_OF_MONTH)

    Card(
        modifier = Modifier.clickable { onDateSelected() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFF6A1B9A) else Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(dayOfWeek, color = if (isSelected) Color.White else Color.Gray, fontSize = 12.sp)
            Text(dayOfMonth.toString(), color = if (isSelected) Color.White else Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}

@Composable
fun TimeSelection(selectedTime: String?, onTimeSelected: (String) -> Unit) {
    val times = listOf("09:00 AM", "10:00 AM", "11:00 AM", "02:00 PM", "04:30 PM")
    Column {
        Text("Select Time", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            times.take(3).forEach { time ->
                TimeBox(time = time, isSelected = selectedTime == time, onTimeSelected = { onTimeSelected(time) }, modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            times.takeLast(2).forEach { time ->
                TimeBox(time = time, isSelected = selectedTime == time, onTimeSelected = { onTimeSelected(time) }, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun TimeBox(time: String, isSelected: Boolean, onTimeSelected: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color(0xFFF3E5F5) else Color.White)
            .border(1.dp, if(isSelected) Color(0xFF6A1B9A) else Color.LightGray, RoundedCornerShape(8.dp))
            .clickable { onTimeSelected() }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(time, color = if (isSelected) Color(0xFF6A1B9A) else Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun BookAppointmentScreenPreview() {
    UroLithAITheme {
        BookAppointmentScreen(rememberNavController())
    }
}
