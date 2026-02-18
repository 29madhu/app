package com.simats.urolithai

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simats.urolithai.ui.theme.UroLithAITheme

class RoleSelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UroLithAITheme {
                RoleSelectionScreen(
                    onNavigateBack = { finish() },
                    onRoleSelected = { role ->
                        val intent = Intent(this, CreateAccountActivity::class.java)
                        intent.putExtra("role", role)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleSelectionScreen(onNavigateBack: () -> Unit, onRoleSelected: (String) -> Unit) {
    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color.White)) {
                TopAppBar(
                    title = { Text("Welcome", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        Text("Step 1/4", modifier = Modifier.padding(end = 16.dp), color = Color.Gray)
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )
                LinearProgressIndicator(
                    progress = { 0.66f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    color = Color(0xFF6A1B9A),
                    trackColor = Color.LightGray.copy(alpha = 0.3f)
                )
            }

        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF3E5F5)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = Color(0xFF6A1B9A)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Who are you?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Select your role to proceed",
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(48.dp))
            RoleCard(
                imageVector = Icons.Default.Person,
                title = "Patient",
                subtitle = "I want to analyze my reports",
                onClick = { onRoleSelected("Patient") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            RoleCard(
                painterRes = R.drawable.doctor,
                title = "Doctor",
                subtitle = "I want to review patients",
                onClick = { onRoleSelected("Doctor") },
                applyTint = false
            )
        }
    }
}

@Composable
fun RoleCard(
    imageVector: ImageVector? = null,
    painterRes: Int? = null,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    applyTint: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF7F2FA))
            .border(1.dp, Color(0xFFEFE6F6), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
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
            if (imageVector != null) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                    tint = Color(0xFF6A1B9A),
                    modifier = Modifier.size(32.dp)
                )
            }
            if (painterRes != null) {
                Icon(
                    painter = painterResource(id = painterRes),
                    contentDescription = null,
                    tint = if(applyTint) Color(0xFF6A1B9A) else Color.Unspecified,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Text(text = subtitle, color = Color.Gray, fontSize = 14.sp)
        }
        Icon(
            painter = painterResource(id = R.drawable.img_5),
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RoleSelectionScreenPreview() {
    UroLithAITheme {
        RoleSelectionScreen(onNavigateBack = {}, onRoleSelected = {})
    }
}
