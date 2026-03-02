
package com.simats.urolithai

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// DATA CLASS
data class Appointment(
    val name: String,
    val time: String,
    val type: String,
    val icon: Int,
    val date: Calendar,
    val phone: String
)

// SAMPLE DATA
val appointmentsList = listOf(
    Appointment("Rajesh Kumar", "09:00 AM", "Video Call", R.drawable.call, Calendar.getInstance().apply { set(2025, Calendar.JANUARY, 15) }, "+911234567890"),
    Appointment("Meera Iyer", "10:30 AM", "In-Person", R.drawable.location, Calendar.getInstance().apply { set(2025, Calendar.JANUARY, 15) }, "+919876543210"),
    Appointment("Suresh Patel", "02:00 PM", "Video Call", R.drawable.call, Calendar.getInstance().apply { set(2025, Calendar.JANUARY, 15) }, "+919998887776")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentsScreen(navController: NavController) {

    var selectedIndex by remember {
        mutableStateOf(0)
    }
    var selectedAppointment by remember { mutableStateOf<Appointment?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Appointments")
                },
                actions = {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate("bookAppointment")
                        },
                        modifier = Modifier.size(40.dp),
                        shape = CircleShape,
                        containerColor = Color(0xFF6A1B9A)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = { DoctorBottomNavigationBar(navController) }
    ) { paddingValues ->


        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                DateSelectionRow(
                    selectedIndex = selectedIndex,
                    onDateSelected = {
                        selectedIndex = it
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Today's Schedule",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Text(
                        "${appointmentsList.size} Appointments",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(appointmentsList) { appointment ->
                        AppointmentCard(appointment) {
                            selectedAppointment = appointment
                        }
                    }
                }
            }

            if (selectedAppointment != null) {
                val context = LocalContext.current
                StartVisitCard(
                    appointment = selectedAppointment!!,
                    onClose = { selectedAppointment = null }) {
                    // Launch the dialer with the appointment phone number
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:${selectedAppointment!!.phone}")
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
}

// DATE SELECTION ROW
@Composable
fun DateSelectionRow(
    selectedIndex: Int,
    onDateSelected: (Int) -> Unit
) {
    val calendar = Calendar.getInstance()

    val dates = List(6) { index ->
        val cal = calendar.clone() as Calendar
        cal.add(Calendar.DAY_OF_YEAR, index)
        cal
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        dates.forEachIndexed { index, date ->
            AppointmentDateBox(
                date = date,
                isSelected = selectedIndex == index,
                onClick = {
                    onDateSelected(index)
                }
            )
        }
    }
}

// RENAMED FUNCTION (NO CONFLICT)
@Composable
fun AppointmentDateBox(
    date: Calendar,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val days = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val dayName = days[date.get(Calendar.DAY_OF_WEEK) - 1]
    val dayNumber = date.get(Calendar.DAY_OF_MONTH)

    Card(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = 
                if (isSelected)
                    Color(0xFF6A1B9A)
                else
                    Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                dayName,
                color = if (isSelected) Color.White else Color.Gray,
                fontSize = 12.sp
            )
            Text(
                dayNumber.toString(),
                color = if (isSelected) Color.White else Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// APPOINTMENT CARD
@Composable
fun AppointmentCard(
    appointment: Appointment,
    onClick: () -> Unit
) {
    val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())
    val dayFormat = SimpleDateFormat("dd", Locale.getDefault())

    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF3E5F5)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${monthFormat.format(appointment.date.time).uppercase()}\n${dayFormat.format(appointment.date.time)}",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6A1B9A)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    appointment.name,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(R.drawable.timer),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        appointment.time,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Icon(
                        painterResource(appointment.icon),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        appointment.type,
                        color = Color.Gray
                    )
                }
            }

                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.Gray
                )
        }
    }
}

@Composable
fun StartVisitCard(appointment: Appointment, onClose: () -> Unit, onStartVisit: () -> Unit) {
    val monthFormat = SimpleDateFormat("MMM dd", Locale.getDefault())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(appointment.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Close",
                        modifier = Modifier.clickable(onClick = onClose)
                    )
                }
                Text(appointment.type, color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5))
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(id = R.drawable.timer), contentDescription = "Time", tint = Color(0xFF6A1B9A))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Time", color = Color.Gray, fontSize = 12.sp)
                            Text("${appointment.time}, ${monthFormat.format(appointment.date.time)}", fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    TextButton(onClick = { /*TODO*/ }) {
                        Text("View History", color = Color(0xFF6A1B9A))
                    }
                    Button(
                        onClick = onStartVisit,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Start Visit")
                    }
                }
            }
        }
    }
}
