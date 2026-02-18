package com.simats.urolithai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.ui.theme.UroLithAITheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportDetailsScreen(
    navController: NavController, 
    uploadId: String?
) {

    val report = uploads.find { it.id == uploadId } ?: uploads.first()

    Scaffold(

        topBar = {

            TopAppBar(

                title = { Text("Report Details") },

                navigationIcon = {

                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {

                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },

        containerColor = Color(0xFFF8F5FA),

        bottomBar = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.weight(1f)
                ) {

                    Icon(
                        painterResource(R.drawable.download),
                        null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Download PDF")
                }

                Button(
                    onClick = { navController.navigate(Screen.ShareReport.route) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6A1B9A)
                    )
                ) {

                    Icon(
                        painterResource(R.drawable.share),
                        null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Share Report")
                }
            }
        }

    ) { padding ->

        Column(

            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())

        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    report.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                StatusChip(report.status)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text("Report ID: ${report.id}", color = Color.Gray)

            Spacer(modifier = Modifier.height(10.dp))

            DetailItem(R.drawable.appoinments, report.date)

            DetailItem(R.drawable.human, report.doctor)

            Spacer(modifier = Modifier.height(24.dp))

            AIAnalysisCard()

            Spacer(modifier = Modifier.height(24.dp))

            DoctorNotesCard()
        }
    }
}


@Composable
fun StatusChip(status: String) {

    val color =
        when (status) {
            "approved" -> Color(0xFF388E3C)
            "pending" -> Color(0xFFFFA000)
            else -> Color.Red
        }

    val bg =
        when (status) {
            "approved" -> Color(0xFFE8F5E9)
            "pending" -> Color(0xFFFFFBEA)
            else -> Color(0xFFFFEBEE)
        }

    Text(
        status.uppercase(),
        color = color,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        modifier = Modifier
            .background(bg, RoundedCornerShape(8.dp))
            .padding(12.dp, 6.dp)
    )
}


@Composable
fun DetailItem(icon: Int, text: String) {

    Row(verticalAlignment = Alignment.CenterVertically) {

        Icon(
            painterResource(icon),
            null,
            tint = Color.Gray
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(text, color = Color.Gray)
    }
}


@Composable
fun AIAnalysisCard() {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF6A1B9A)
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                "AI Analysis Result",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Stone Type: Calcium Oxalate", color = Color.White)

            Text("Size: 5 mm", color = Color.White)

            Text("Location: Left Kidney", color = Color.White)
        }
    }
}


@Composable
fun DoctorNotesCard() {

    Card {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                "Doctor's Notes",
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "Patient has small kidney stone. Drink more water and review after 2 weeks."
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportDetailsScreenPreview() {
    UroLithAITheme {
        ReportDetailsScreen(rememberNavController(), "rpt-002")
    }
}
