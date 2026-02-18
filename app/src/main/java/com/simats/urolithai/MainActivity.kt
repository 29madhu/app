package com.simats.urolithai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
                        val screensWithoutBottomBar = listOf(
                            Screen.Splash.route,
                            Screen.RoleSelection.route,
                            Screen.Login.route,
                            Screen.Signup.route,
                            Screen.ForgotPassword.route,
                            Screen.VoiceCall.route,
                            Screen.VideoCall.route,
                            Screen.AccountCreated.route,
                            Screen.PasswordResetSuccess.route,
                            Screen.CreateAccount.route,
                            Screen.OtpVerification.route
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
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) { SplashScreen(onTimeout = { navController.navigate(Screen.RoleSelection.route) }) }
        composable(Screen.RoleSelection.route) { RoleSelectionScreen(onNavigateBack = { navController.popBackStack() }, onRoleSelected = { role -> navController.navigate(Screen.CreateAccount.createRoute(role)) }) }
        composable(Screen.Login.route) { LoginScreen(onLoginSuccess = { navController.navigate(Screen.Home.route) }, onRegister = { navController.navigate(Screen.Signup.route) }, onForgotPassword = { navController.navigate(Screen.ForgotPassword.route)}, role = "") }
        composable(Screen.Signup.route) { SignupScreen(onSignupSuccess = { navController.navigate(Screen.Login.route) }, onLogin = { navController.navigate(Screen.Login.route) }, onTerms = {}, onPolicy = {}) }
        composable(Screen.ForgotPassword.route) { ForgotPasswordScreen(onResetSuccess = { navController.navigate(Screen.Login.route) }) }
        composable(Screen.Home.route) { DashboardScreen(navController) }
        composable(Screen.Reports.route) { MyReportsScreen(navController) }
        composable(Screen.Appointments.route) { AppointmentsScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }
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
        composable(Screen.MyReports.route) { MyReportsScreen(navController) }
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
        composable(Screen.FindSpecialist.route) { FindSpecialistScreen() }
        composable(Screen.UploadHistory.route) { UploadHistoryScreen() }
        composable(Screen.DietPrecautions.route) { DietPrecautionsScreen() }
        composable(Screen.MyPrescriptions.route) { MyPrescriptionsScreen() }
        composable(Screen.RateExperience.route) { RateExperienceScreen() }
        composable(Screen.ShareReport.route) { ShareReport() }
        composable(Screen.AccountCreated.route) { backStackEntry ->
            AccountCreatedScreen(navController, backStackEntry.arguments?.getString("role"))
        }
        composable(Screen.PasswordResetSuccess.route) { PasswordResetSuccessScreen(navController) }
        composable(Screen.CreateAccount.route) { backStackEntry ->
            CreateAccountScreen(onNavigateBack = { navController.popBackStack() }, onVerifyMobile = { role -> navController.navigate(Screen.OtpVerification.createRoute(role))}, role = backStackEntry.arguments?.getString("role") ?: "Patient")
        }
        composable(Screen.OtpVerification.route) { backStackEntry ->
            OtpVerificationScreen(navController = navController, onVerifyAndRegister = { navController.navigate(Screen.AccountCreated.createRoute(backStackEntry.arguments?.getString("role") ?: "Patient")) })
        }
    }
}
