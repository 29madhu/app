package com.simats.urolithai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.simats.urolithai.ui.theme.UroLithAITheme

class DoctorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UroLithAITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "dashboard") {
                        composable("dashboard") { DoctorDashboardScreen(navController) }
                        composable(
                            "cases/{status}",
                            arguments = listOf(navArgument("status") { type = NavType.StringType })
                        ) { backStackEntry ->
                            DoctorCasesScreen(navController, backStackEntry.arguments?.getString("status") ?: "Approved")
                        }
                        composable("review_case/{caseId}") { backStackEntry ->
                            DoctorReviewCaseScreen(navController, backStackEntry.arguments?.getString("caseId"))
                        }
                        composable("rejected_case_reason/{caseId}") { backStackEntry ->
                            DoctorRejectedCaseReasonScreen(navController, backStackEntry.arguments?.getString("caseId"))
                        }
                        composable("ai_analysis/{caseId}") { backStackEntry ->
                            DoctorAiAnalysisScreen(navController, backStackEntry.arguments?.getString("caseId"))
                        }
                        composable("write_prescription") { WritePrescriptionScreen(navController) }
                        composable("preview_prescription") { PreviewPrescriptionScreen(navController) }
                        composable("prescription_sent") { PrescriptionSentScreen(navController) }
                        composable("patient_history") { PatientHistoryScreen(navController) }
                        composable("messages") { DoctorMessagesScreen(navController) }
                        composable("appointments") { AppointmentsScreen(navController) }
                        composable("individual_chat") { DoctorIndividualChatScreen(navController) }
                        // video call screen removed; use external dialer or dedicated implementation
                        composable("analytics") { AnalyticsScreen(navController) }
                        composable("settings") { DoctorSettingsScreen(navController) }
                        composable("doctorEditProfile") { DoctorEditProfileScreen(navController) }
                        composable("doctorHelpAndFaqs") { DoctorHelpAndFaqsScreen(navController) }
                        composable("doctorAppInfo") { DoctorAppInfoScreen(navController) }
                        composable("doctorPrivacyPolicy") { DoctorPrivacyPolicyScreen(navController) }
                        composable("logout") { DoctorLogoutScreen(navController) }
                        composable("doctorNotifications") { DoctorNotificationsScreen(navController) }
                    }
                }
            }
        }
    }
}
