package com.simats.urolithai

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorIndividualChatScreen(navController: NavController) {

    val context = LocalContext.current
    val currentUser = "doctor"
    val messages = ChatRepository.messages

    var text by remember { mutableStateOf("") }
    var selectedFile by remember { mutableStateOf<Uri?>(null) }

    val filePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        selectedFile = uri
    }

    Scaffold(
        containerColor = Color(0xFFF8F9FA),

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
                            Text("RK", fontWeight = FontWeight.Bold, color = Color(0xFF7B1FA2))
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text("Rajesh Kumar", fontSize = 16.sp, fontWeight = FontWeight.Bold)

                            Row(verticalAlignment = Alignment.CenterVertically) {

                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF4CAF50))
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    "Online",
                                    fontSize = 12.sp,
                                    color = Color(0xFF4CAF50)
                                )
                            }
                        }
                    }
                },

                navigationIcon = {

                    IconButton(onClick = { navController.popBackStack() }) {

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF555555)
                        )
                    }
                },

                actions = {

                    IconButton(onClick = {

                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:9876543210"))
                        context.startActivity(intent)

                    }) {

                        Icon(
                            painter = painterResource(id = R.drawable.call),
                            contentDescription = "Call",
                            tint = Color(0xFF555555),
                            modifier = Modifier.size(24.dp)
                        )
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
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = { filePicker.launch("*/*") }) {

                        Icon(
                            painter = painterResource(id = R.drawable.img_24),
                            contentDescription = "Attach",
                            tint = Color.Gray,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        placeholder = { Text("Type message...", color = Color.LightGray) },
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 48.dp),

                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),

                        shape = RoundedCornerShape(24.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(

                        onClick = {

                            val time = SimpleDateFormat(
                                "hh:mm a",
                                Locale.getDefault()
                            ).format(Date())

                            if (text.isNotBlank()) {

                                ChatRepository.addMessage(
                                    ChatMessage(
                                        message = text,
                                        time = time,
                                        sender = currentUser,
                                        isRead = true
                                    )
                                )

                                text = ""
                            }

                            if (selectedFile != null) {

                                ChatRepository.addMessage(
                                    ChatMessage(
                                        message = "📎 File Attached",
                                        time = time,
                                        sender = currentUser,
                                        isRead = true
                                    )
                                )

                                selectedFile = null
                            }

                        },

                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF7B1FA2))
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.send),
                            contentDescription = "Send",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

    ) { padding ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)

        ) {

            item {

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {

                    Surface(
                        color = Color(0xFFEEEEEE),
                        shape = RoundedCornerShape(8.dp)
                    ) {

                        Text(
                            "Today",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            items(messages) { message ->

                val isMyMessage = message.sender == currentUser

                ChatMessageItem(message, isMyMessage)

            }

        }

    }

}

@Composable
fun ChatMessageItem(message: ChatMessage, isMyMessage: Boolean) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isMyMessage) Alignment.End else Alignment.Start
    ) {

        val backgroundColor = if (isMyMessage) Color(0xFF7B1FA2) else Color.White
        val textColor = if (isMyMessage) Color.White else Color.Black

        Box(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isMyMessage) 16.dp else 0.dp,
                        bottomEnd = if (isMyMessage) 0.dp else 16.dp
                    )
                )
                .background(backgroundColor)
                .padding(12.dp)
        ) {

            Text(
                text = message.message,
                color = textColor,
                fontSize = 15.sp,
                lineHeight = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (isMyMessage) Arrangement.End else Arrangement.Start
        ) {

            Text(
                text = message.time,
                fontSize = 11.sp,
                color = Color.Gray
            )

            if (isMyMessage) {

                Spacer(modifier = Modifier.width(4.dp))

                Icon(
                    imageVector = Icons.Default.DoneAll,
                    contentDescription = null,
                    tint = if (message.isRead) Color(0xFF7B1FA2) else Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}