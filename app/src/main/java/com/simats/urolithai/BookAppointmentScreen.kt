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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointmentScreen(navController: NavController) {
    var selectedConsultationType by remember { mutableStateOf("Voice Call") }
    var selectedDate by remember { mutableStateOf(Calendar.getInstance().apply { set(Calendar.DAY_OF_MONTH, 15) }) }
    var selectedTime by remember { mutableStateOf<String?>("09:00 AM") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Book Appointment") },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") } },
                actions = { IconButton(onClick = { /*TODO*/ }) { Icon(painterResource(id = R.drawable.call), contentDescription = "Call") } },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF8F5FA)
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp)
        ) {
            DoctorInfoCard()
            Spacer(modifier = Modifier.height(24.dp))
            Text("Consultation Type", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ConsultationTypeChip(text = "Voice Call", icon = R.drawable.call, isSelected = selectedConsultationType == "Voice Call", onClick = { selectedConsultationType = "Voice Call" }, modifier = Modifier.weight(1f))
                ConsultationTypeChip(text = "Clinic Visit", icon = R.drawable.location, isSelected = selectedConsultationType == "Clinic Visit", onClick = { selectedConsultationType = "Clinic Visit" }, modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("Select Date", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            DateSelector(selectedDate = selectedDate, onDateSelected = { selectedDate = it })
            Spacer(modifier = Modifier.height(24.dp))
            Text("Select Time", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            TimeSelector(selectedTime = selectedTime, onTimeSelected = { selectedTime = it })
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { navController.navigate(Screen.Appointments.route) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A))
            ) {
                Text("Confirm Appointment", fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun DoctorInfoCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(48.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF6A1B9A)),
                contentAlignment = Alignment.Center
            ) {
                Text("PS", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Dr. Priya Sharma", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Urologist • Apollo Hospitals", color = Color.Gray, fontSize = 14.sp)
                Text("₹500 Consultation Fee", color = Color(0xFF6A1B9A), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun ConsultationTypeChip(text: String, icon: Int, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val backgroundColor = if (isSelected) Color(0xFFEFE6F6) else Color.White
    val borderColor = if (isSelected) Color(0xFFD1C4E9) else Color.LightGray
    val contentColor = if (isSelected) Color(0xFF6A1B9A) else Color.Gray
    
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(painterResource(id = icon), contentDescription = text, tint = contentColor)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = contentColor, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun DateSelector(selectedDate: Calendar, onDateSelected: (Calendar) -> Unit) {
    val dates = remember {
        val calendar = Calendar.getInstance()
        (0..4).map {
            val newCal = calendar.clone() as Calendar
            newCal.add(Calendar.DAY_OF_YEAR, it)
            newCal
        }
    }
    
    LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        items(dates) { date ->
            val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())
            val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
            val isSelected = selectedDate.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR)

            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) Color(0xFF6A1B9A) else Color.Transparent)
                    .clickable { onDateSelected(date) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(dayFormat.format(date.time), color = if (isSelected) Color.White else Color.Gray)
                Text(dateFormat.format(date.time), color = if (isSelected) Color.White else Color.Black, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TimeSelector(selectedTime: String?, onTimeSelected: (String) -> Unit) {
    val timeSlots = listOf("09:00 AM", "10:30 AM", "11:00 AM", "02:00 PM", "04:30 PM")
    
    LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(timeSlots) { time ->
            val isSelected = selectedTime == time
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) Color(0xFFEFE6F6) else Color.White)
                    .border(1.dp, if (isSelected) Color(0xFFD1C4E9) else Color.LightGray, RoundedCornerShape(12.dp))
                    .clickable { onTimeSelected(time) }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(time, color = if (isSelected) Color(0xFF6A1B9A) else Color.Black)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookAppointmentScreenPreview() {
    UroLithAITheme {
        BookAppointmentScreen(rememberNavController())
    }
}
