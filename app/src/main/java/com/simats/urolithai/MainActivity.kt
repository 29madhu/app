package com.simats.urolithai

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.ui.theme.UroLithAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UroLithAITheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route
                        // Screens that should not have the bottom bar
                        val screensWithoutBottomBar = listOf(
                            Screen.Login.route,
                            Screen.Signup.route,
                            Screen.ForgotPassword.route,
                            Screen.VoiceCall.route,
                            Screen.VideoCall.route,
                            Screen.BookAppointment.route,
                            Screen.Appointments.route,
                            Screen.Chat.route,

                            Screen.DoctorDetails.route,
                            Screen.ReportDetails.route,
                            Screen.ReportSent.route
                        )
                        if (currentRoute !in screensWithoutBottomBar) {
                            BottomNavigationBar(navController)
                        }
                    }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        AppNavigation(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    val context = LocalContext.current
    // Navigation graph for the main part of the app, starting with Login
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) { 
            LoginScreen(
                onLoginSuccess = { 
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onForgotPassword = { context.startActivity(Intent(context, ResetPasswordActivity::class.java)) }
            )
        }
        composable(Screen.Signup.route) { SignupScreen(onSignupSuccess = { navController.navigate(Screen.Login.route) }, onLogin = { navController.navigate(Screen.Login.route) }, onTerms = {}, onPolicy = {}) }
        
        // Main dashboard screens
        composable(Screen.Home.route) { DashboardScreen(navController) }
        composable(Screen.Reports.route) { MyReportsScreen(navController) }
        composable(Screen.Appointments.route) { AppointmentsScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }

        // Feature screens
        composable(Screen.Chat.route) { backStackEntry ->
            ChatScreen(navController, backStackEntry.arguments?.getString("doctorName"))
        }
        composable(Screen.VoiceCall.route) { backStackEntry ->
            VoiceCallScreen(navController, backStackEntry.arguments?.getString("doctorName"))
        }
        composable(Screen.VideoCall.route) { backStackEntry ->
            VideoCallScreen(navController, backStackEntry.arguments?.getString("doctorName"))
        }
        composable(Screen.BookAppointment.route) { BookAppointmentScreen(navController) }
        composable(Screen.DoctorDetails.route) { backStackEntry ->
            DoctorProfileScreen(navController, backStackEntry.arguments?.getString("doctorName"))
        }
        composable(Screen.ReportDetails.route) { backStackEntry ->
            ReportDetailsScreen(navController, backStackEntry.arguments?.getString("uploadId"))
        }
        composable(Screen.ReportSent.route) { backStackEntry ->
            ReportSentScreen(navController, backStackEntry.arguments?.getString("doctorName"))
        }
        composable(Screen.DoctorProfile.route) { backStackEntry ->
            DoctorProfileScreen(navController, backStackEntry.arguments?.getString("specialistInitials"))
        }
        composable(Screen.Notifications.route) { NotificationsScreen() }
        composable(Screen.FindSpecialist.route) { FindDoctorScreen(navController) }
        composable(Screen.UploadHistory.route) { UploadHistoryScreen() }
        composable(Screen.DietPrecautions.route) { DietPrecautionsScreen() }
        composable(Screen.MyPrescriptions.route) { MyPrescriptionsScreen() }
        composable(Screen.RateExperience.route) { RateExperienceScreen() }
        composable(Screen.ShareReport.route) { ShareReport() }
        composable(Screen.FindDoctor.route) { FindDoctorScreen(navController) }
    }
}
