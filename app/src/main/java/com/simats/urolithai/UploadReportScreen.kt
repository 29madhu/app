package com.simats.urolithai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.rememberCoroutineScope
import com.simats.urolithai.network.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import com.simats.urolithai.ui.theme.UroLithAITheme
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.draw.clip
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun UploadReportScreen(navController: NavController, doctorName: String?) {
    var selectedReportType by remember { mutableStateOf("Ultrasound") }
    val reportTypes = listOf("Ultrasound", "CT Scan", "X-Ray", "Blood Test")
    var notes by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var fileName by remember { mutableStateOf<String?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val apiService = remember { RetrofitClient.getApiService(context) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        fileName = uri?.lastPathSegment
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Upload Report") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Button(
                onClick = { 
                    selectedImageUri?.let { uri ->
                        isUploading = true
                        scope.launch {
                            try {
                                // Convert Uri to File (Helper needed for real apps, simplified here)
                                val file = File(context.cacheDir, "upload_image.jpg")
                                context.contentResolver.openInputStream(uri)?.use { input ->
                                    FileOutputStream(file).use { output ->
                                        input.copyTo(output)
                                    }
                                }

                                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                                val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

                                val response = apiService.uploadReport(body)
                                if (response.isSuccessful) {
                                    navController.navigate("reportSent/${doctorName ?: "Doctor"}")
                                } else {
                                    errorMessage = "Upload failed: ${response.message()}"
                                }
                            } catch (e: Exception) {
                                errorMessage = "Error: ${e.localizedMessage}"
                            } finally {
                                isUploading = false
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
                enabled = selectedImageUri != null && !isUploading
            ) {
                Text(text = if (isUploading) "Uploading..." else "Submit Report", fontSize = 18.sp)
            }
        },
        containerColor = Color(0xFFF8F5FA)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF3E5F5)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("RK", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF6A1B9A))
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Column {
                        Text("Sending to", color = Color.Gray, fontSize = 14.sp)
                        Text(doctorName ?: "Dr. Ravi Kumar", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text("Report File", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            if (selectedImageUri == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { launcher.launch("image/*") }
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painter = painterResource(id = R.drawable.upload), contentDescription = "Upload", tint = Color(0xFF6A1B9A), modifier = Modifier.size(40.dp))
                        Text("Upload Report", color = Color(0xFF6A1B9A), fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(id = R.drawable.image), contentDescription = null, modifier = Modifier.size(40.dp))
                        Spacer(modifier = Modifier.size(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(fileName ?: "Selected Image", fontWeight = FontWeight.Bold)
                            Text("Image selected", color = Color.Gray, fontSize = 12.sp)
                        }
                        IconButton(onClick = { selectedImageUri = null; fileName = null }) {
                            Icon(Icons.Default.Close, contentDescription = "Remove")
                        }
                    }
                }
            }

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(errorMessage!!, color = Color.Red, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Report Type", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                reportTypes.forEach { type ->
                    FilterChip(
                        selected = selectedReportType == type,
                        onClick = { selectedReportType = type },
                        label = { Text(type) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Notes (Optional)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Add any additional details for the doctor...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF6A1B9A),
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UploadReportScreenPreview() {
    UroLithAITheme {
        UploadReportScreen(rememberNavController(), "Dr. Ravi Kumar")
    }
}
