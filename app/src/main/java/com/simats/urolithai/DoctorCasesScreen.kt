package com.simats.urolithai

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController



// NOTE: `Case` data class is defined centrally in `Data.kt` to avoid duplicate declarations.

data class FollowUpCase(
    val id: String,
    val name: String,
    val time: String,
    val date: String
)


// ✅ SAMPLE DATA

val pendingCasesList = listOf(
    Case("RPT-001", "Ultrasound Left Kidney", "Ultrasound", "12 Jan 2025", "Pending"),
    Case("RPT-004", "Ultrasound Follow-up", "Ultrasound", "15 Jan 2025", "Pending")
)

val approvedCasesList = listOf(
    Case("RPT-001", "Ultrasound Left Kidney", "Ultrasound", "12 Jan 2025", "Approved"),
    Case("RPT-002", "CT Scan Bilateral", "CT Scan", "08 Jan 2025", "Approved"),
    Case("RPT-003", "Ultrasound Follow-up", "Ultrasound", "15 Jan 2025", "Approved")
)

val rejectedCasesList = listOf(
    Case("RPT-005", "X-Ray KUB", "X-Ray", "01 Jan 2025", "Rejected")
)

val followUpCasesList = listOf(
    FollowUpCase("PAT-2024-00422", "Rahul Sharma", "Tomorrow", "15 Dec")
)


// ✅ MAIN SCREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorCasesScreen(
    navController: NavController,
    initialStatus: String? = "Approved"
) {

    // Map the incoming status (string) to the tab index used in this screen
    val statusToIndex = mapOf("Pending" to 0, "Approved" to 1, "Rejected" to 2, "Follow-up" to 3)
    var selectedTabIndex by remember {
        mutableStateOf(statusToIndex[initialStatus] ?: 1)
    }

    val tabs = listOf("Pending", "Approved", "Rejected", "Follow-up")

    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {
                    Text("Case Management")
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )

            )

        },

        bottomBar = {
            DoctorBottomNavigationBar(navController)
        }

    ) { paddingValues ->

        Column(

            modifier = Modifier
                .padding(paddingValues)

        ) {

            TabRow(

                selectedTabIndex = selectedTabIndex,

                containerColor = Color.White,

                indicator = {

                    TabRowDefaults.PrimaryIndicator(

                        modifier =
                            Modifier.tabIndicatorOffset(
                                it[selectedTabIndex]
                            ),

                        color = Color(0xFF6A1B9A)

                    )

                }

            ) {

                tabs.forEachIndexed { index, title ->

                    Tab(

                        selected = selectedTabIndex == index,

                        onClick = {
                            selectedTabIndex = index
                        },

                        text = {
                            Text(title)
                        }

                    )

                }

            }


            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF8F5FA))
                    .padding(16.dp),

                verticalArrangement = Arrangement.spacedBy(16.dp)

            ) {

                when (selectedTabIndex) {

                    0 -> items(pendingCasesList) {

                        CaseItemCard(navController, it)

                    }

                    1 -> items(approvedCasesList) {

                        LocalApprovedCaseItemCard(navController, it)

                    }

                    2 -> items(rejectedCasesList) {

                        LocalRejectedCaseItemCard(navController, it)

                    }

                    3 -> items(followUpCasesList) {

                        LocalFollowUpCaseItemCard(navController, it)

                    }

                }

            }

        }

    }

}


// ✅ PENDING CARD

@Composable
fun CaseItemCard(navController: NavController, case: Case) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                navController.navigate(
                    "review_case/${case.id}"
                )

            },

        shape = RoundedCornerShape(16.dp)

    ) {

        Column(

            modifier = Modifier.padding(16.dp)

        ) {

            Text(
                case.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Text(
                case.id,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(

                onClick = {

                    navController.navigate(
                        "review_case/${case.id}"
                    )

                },

                modifier = Modifier.fillMaxWidth(),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A1B9A)
                )

            ) {

                Text("Review Case")

            }

        }

    }

}


// ✅ APPROVED CARD

@Composable
fun LocalApprovedCaseItemCard(navController: NavController, case: Case) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp)

    ) {

        Column(

            modifier = Modifier.padding(16.dp)

        ) {

            Text(
                case.title,
                fontWeight = FontWeight.Bold
            )

            Text(case.id)

            Button(

                onClick = {

                    navController.navigate(
                        "patient_history"
                    )

                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("View Details")

            }

        }

    }

}


// ✅ REJECTED CARD

@Composable
fun LocalRejectedCaseItemCard(navController: NavController, case: Case) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp)

    ) {

        Column(

            modifier = Modifier.padding(16.dp)

        ) {

            Text(case.title)

            Button(

                onClick = {

                    navController.navigate(
                        "rejected_case_reason/${case.id}"
                    )

                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("View Reason")

            }

        }

    }

}


// ✅ FOLLOW-UP CARD

@Composable
fun LocalFollowUpCaseItemCard(navController: NavController, case: FollowUpCase) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp)

    ) {

        Column(

            modifier = Modifier.padding(16.dp)

        ) {

            Text(
                case.name,
                fontWeight = FontWeight.Bold
            )

            Text(case.id)

            Button(

                onClick = {

                    navController.navigate(
                        "appointments"
                    )

                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Schedule Appointment")

            }

        }

    }

}