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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Data class for chat messages
data class ChatMessage(val message: String, val time: String, val isSentByMe: Boolean, val isRead: Boolean = false)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorIndividualChatScreen(navController: NavController) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val messages = ChatRepository.messages

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFEBE0F5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("RK", fontWeight = FontWeight.Bold, color = Color(0xFF6A1B9A))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Rajesh Kumar", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color.Green))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Online", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painterResource(id = R.drawable.call), contentDescription = "Call", modifier = Modifier.size(24.dp), tint = Color(0xFF6A1B9A))
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Videocam, contentDescription = "Video Call", modifier = Modifier.size(24.dp), tint = Color(0xFF6A1B9A))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Surface(color = Color.White, shadowElevation = 8.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var text by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        placeholder = { Text("Type a message....") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5),
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FloatingActionButton(
                        onClick = {
                            if (text.isNotBlank()) {
                                val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
                                ChatRepository.addMessage(
                                    ChatMessage(
                                        message = text,
                                        time = currentTime,
                                        isSentByMe = true,
                                        isRead = true
                                    )
                                )
                                text = ""
                            }
                        },
                        modifier = Modifier.size(48.dp),
                        containerColor = Color(0xFF6A1B9A),
                        elevation = FloatingActionButtonDefaults.elevation(0.dp)
                    ) {
                        Icon(painterResource(id = R.drawable.send), contentDescription = "Send", tint = Color.White)
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFAFAFA))
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                ChatMessageItem(message)
            }
        }

        LaunchedEffect(messages.size) {
            coroutineScope.launch {
                listState.scrollToItem(messages.size - 1)
            }
        }
    }
}

@Composable
fun ChatMessageItem(message: ChatMessage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = if (message.isSentByMe) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (message.isSentByMe) 16.dp else 0.dp,
                        bottomEnd = if (message.isSentByMe) 0.dp else 16.dp
                    )
                )
                .background(if (message.isSentByMe) Color(0xFF6A1B9A) else Color.White)
                .padding(12.dp)
        ) {
            Text(
                text = message.message,
                color = if (message.isSentByMe) Color.White else Color.Black
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(message.time, fontSize = 10.sp, color = Color.Gray)
            if (message.isSentByMe && message.isRead) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    Icons.Default.DoneAll,
                    contentDescription = "Read",
                    tint = Color(0xFF6A1B9A),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DoctorIndividualChatScreenPreview() {
    UroLithAITheme {
        DoctorIndividualChatScreen(rememberNavController())
    }
}
