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
                            "cases/{initialTabIndex}",
                            arguments = listOf(navArgument("initialTabIndex") { type = NavType.IntType })
                        ) { backStackEntry ->
                            DoctorCasesScreen(navController, backStackEntry.arguments?.getInt("initialTabIndex"))
                        }
                        composable("review_case/{caseId}") { backStackEntry ->
                            DoctorReviewCaseScreen(navController, backStackEntry.arguments?.getString("caseId"))
                        }
                        composable("ai_analysis/{caseId}") { backStackEntry ->
                            DoctorAiAnalysisScreen(navController, backStackEntry.arguments?.getString("caseId"))
                        }
                        composable("write_prescription") { WritePrescriptionScreen(navController) }
                        composable("preview_prescription") { PreviewPrescriptionScreen(navController) }
                        composable("prescription_sent") { PrescriptionSentScreen(navController) }
                        composable("patient_history") { PatientHistoryScreen(navController) }
                    }
                }
            }
        }
    }
}
