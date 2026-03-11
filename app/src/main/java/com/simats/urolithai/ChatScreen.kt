package com.simats.urolithai

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.simats.urolithai.network.RetrofitClient
import com.simats.urolithai.network.SendMessageRequest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, doctorName: String?) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val apiService = remember { RetrofitClient.getApiService(context) }

    val currentUser = "patient"
    var messageText by remember { mutableStateOf("") }

    val apiMessages = remember { mutableStateListOf<ChatMessage>() }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = apiService.getChatHistory()
                if (response.isSuccessful) {
                    response.body()?.forEach {
                        apiMessages.add(
                            ChatMessage(
                                message = it,
                                time = "Just now",
                                sender = "doctor",
                                isRead = true
                            )
                        )
                    }
                }
            } finally {
                isLoading = false
            }
        }
    }

    var selectedFile by remember { mutableStateOf<Uri?>(null) }

    val filePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> selectedFile = uri }

    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(doctorName ?: "Dr. Priya Sharma", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:9876543210"))
                        context.startActivity(intent)
                    }) {
                        Icon(painterResource(id = R.drawable.call), "Call")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.MoreVert, "More")
                    }
                }
            )
        },

        bottomBar = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(onClick = { filePicker.launch("*/*") }) {
                    Icon(painterResource(id = R.drawable.img_24), "Attach")
                }

                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type message") }
                )

                IconButton(onClick = {

                    if (messageText.isNotBlank()) {

                        scope.launch {

                            val response = apiService.sendMessage(
                                SendMessageRequest(
                                    message = messageText,
                                    sender_id = 1
                                )
                            )

                            if (response.isSuccessful) {

                                apiMessages.add(
                                    ChatMessage(
                                        message = messageText,
                                        time = "Just now",
                                        sender = currentUser
                                    )
                                )

                                messageText = ""
                            }
                        }
                    }
                }) {

                    Icon(
                        painter = painterResource(id = R.drawable.send),
                        contentDescription = "Send"
                    )

                }
            }
        }

    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            items(apiMessages) { message ->

                PatientMessageItem(
                    message = message,
                    isMyMessage = message.sender == currentUser
                )

            }
        }
    }
}

@Composable
fun PatientMessageItem(message: ChatMessage, isMyMessage: Boolean) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment =
            if (isMyMessage) Alignment.End else Alignment.Start
    ) {

        Surface(
            color = if (isMyMessage) Color(0xFF7B1FA2) else Color.White,
            shape = RoundedCornerShape(12.dp)
        ) {

            Text(
                message.message,
                modifier = Modifier.padding(12.dp),
                color = if (isMyMessage) Color.White else Color.Black
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {

            Text(
                message.time,
                fontSize = 11.sp,
                color = Color.Gray
            )

            if (isMyMessage) {
                Icon(
                    Icons.Default.DoneAll,
                    null,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}