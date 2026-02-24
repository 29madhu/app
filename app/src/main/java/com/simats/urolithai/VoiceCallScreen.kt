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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.ui.theme.UroLithAITheme
import kotlinx.coroutines.delay

@Composable
fun VoiceCallScreen(navController: NavController, doctorName: String?) {
    var callTime by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            callTime++
        }
    }

    Scaffold(
        containerColor = Color(0xFF6A1B9A)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Green))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Encrypted Call", color = Color.White.copy(alpha = 0.7f))
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                // You can put a doctor image here
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(doctorName ?: "Dr. Priya Sharma", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                String.format("%02d:%02d", callTime / 60, callTime % 60),
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("Urologist • Apollo Hospitals", color = Color.White.copy(alpha = 0.7f), fontSize = 16.sp)
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CallControlButton(icon = R.drawable.m, onClick = { /* Mute */ })
                CallControlButton(icon = R.drawable.call, onClick = { navController.popBackStack() }, containerColor = Color.Red)
                CallControlButton(icon = R.drawable.speaker, onClick = { /* Speaker */ })
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun CallControlButton(icon: Int, onClick: () -> Unit, containerColor: Color = Color.White.copy(alpha = 0.2f)) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(containerColor)
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun VoiceCallScreenPreview() {
    UroLithAITheme {
        VoiceCallScreen(rememberNavController(), "Dr. Priya Sharma")
    }
}
