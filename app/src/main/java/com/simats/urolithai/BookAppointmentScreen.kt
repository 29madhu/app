package com.simats.urolithai

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import com.simats.urolithai.network.RetrofitClient
import com.simats.urolithai.ui.theme.UroLithAITheme
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun BookAppointmentScreen(navController: NavController, doctorName: String?) {

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
                title = { Text("Book Appointment") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        },

        bottomBar = {

            Button(
                onClick = {

                    selectedImageUri?.let { uri ->

                        isUploading = true

                        scope.launch {

                            try {

                                val file = File(context.cacheDir, "upload.jpg")

                                context.contentResolver.openInputStream(uri)?.use { input ->
                                    FileOutputStream(file).use { output ->
                                        input.copyTo(output)
                                    }
                                }

                                val requestFile =
                                    file.asRequestBody("image/*".toMediaTypeOrNull())

                                val body = MultipartBody.Part.createFormData(
                                    "image",
                                    file.name,
                                    requestFile
                                )

                                val response = apiService.uploadReport(body)

                                if (response.isSuccessful) {

                                    navController.navigate(
                                        "reportSent/${doctorName ?: "Doctor"}"
                                    )

                                } else {

                                    errorMessage = "Upload failed"

                                }

                            } catch (e: Exception) {

                                errorMessage = e.message

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

                enabled = selectedImageUri != null && !isUploading
            ) {

                Text(if (isUploading) "Uploading..." else "Submit")

            }

        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEDE7F6)),
                        contentAlignment = Alignment.Center
                    ) {

                        Text("DR", fontWeight = FontWeight.Bold)

                    }

                    Spacer(Modifier.width(16.dp))

                    Column {

                        Text("Doctor")

                        Text(
                            doctorName ?: "Dr. Ravi Kumar",
                            fontWeight = FontWeight.Bold
                        )

                    }

                }

            }

            Spacer(Modifier.height(24.dp))

            Text("Upload Medical Report", fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(8.dp))

            if (selectedImageUri == null) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                        .clickable { launcher.launch("image/*") }
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Icon(Icons.Default.Upload, null)

                        Text("Upload Report")

                    }

                }

            } else {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(Icons.Default.Image, null)

                    Spacer(Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {

                        Text(fileName ?: "Selected Image")

                        Text("Ready to upload", fontSize = 12.sp)

                    }

                    IconButton(onClick = {

                        selectedImageUri = null
                        fileName = null

                    }) {

                        Icon(Icons.Default.Close, null)

                    }

                }

            }

            errorMessage?.let {

                Spacer(Modifier.height(8.dp))

                Text(it, color = Color.Red)

            }

            Spacer(Modifier.height(24.dp))

            Text("Report Type", fontWeight = FontWeight.Bold)

            Spacer(Modifier.height(8.dp))

            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                reportTypes.forEach { type ->

                    FilterChip(
                        selected = selectedReportType == type,
                        onClick = { selectedReportType = type },
                        label = { Text(type) }
                    )

                }

            }

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Additional Notes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

        }

    }

}

@Preview(showBackground = true)
@Composable
fun BookAppointmentPreview() {

    UroLithAITheme {

        BookAppointmentScreen(
            rememberNavController(),
            "Dr. Ravi Kumar"
        )

    }

}