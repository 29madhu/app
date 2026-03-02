package com.simats.urolithai

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simats.urolithai.ui.theme.UroLithAITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UroLithAITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    val prefs = this@MainActivity.getSharedPreferences("UroLithAIPrefs", MODE_PRIVATE)
                    val onboardingDone = prefs.getBoolean("onboarding_completed", false)
                    val startDestination = if (onboardingDone) "login" else "onboarding"

                    NavHost(navController = navController, startDestination = startDestination) {
                        composable("onboarding") {
                            startActivity(Intent(this@MainActivity, OnboardingActivity::class.java))
                            finish()
                        }
                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = { role ->
                                    if (role == "Patient") {
                                        navController.navigate("dashboard")
                                    } else {
                                        launchDoctorActivity()
                                    }
                                },
                                onForgotPassword = { navController.navigate("forgotPassword") }
                            )
                        }
                        composable("signup") {
                            SignupScreen(
                                onSignupSuccess = { navController.navigate("login") },
                                onLogin = { navController.navigate("login") },
                                onTerms = { navController.navigate("termsAndConditions") },
                                onPolicy = { navController.navigate("privacyPolicy") }
                            )
                        }
                        composable("forgotPassword") {
                            ForgotPasswordScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onSendOtp = { navController.navigate("otpVerification") }
                            )
                        }
                        composable("otpVerification") {
                            OtpVerificationScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onVerifyAndRegister = { navController.navigate("passwordResetSuccess") }
                            )
                        }
                        composable("resetPassword") {
                            startActivity(Intent(this@MainActivity, ResetPasswordActivity::class.java))
                        }
                        composable("passwordResetSuccess") { PasswordResetSuccessScreen(navController) }
                        composable("dashboard") { DashboardScreen(navController) }
                        composable("myReports") { MyReportsScreen(navController) }
                        composable("reportDetails/{uploadId}") { backStackEntry ->
                            val uploadId = backStackEntry.arguments?.getString("uploadId") ?: ""
                            ReportDetailsScreen(navController = navController, uploadId = uploadId)
                        }
                        composable("shareReport") { ShareReportScreen(navController) }
                        composable("reportSent/{doctorName}") { backStackEntry ->
                            val doctorName = backStackEntry.arguments?.getString("doctorName") ?: ""
                            ReportSentScreen(navController, doctorName)
                        }
                        composable("uploadReport") { UploadReportScreen(
                            navController,
                            doctorName = TODO()
                        ) }
                        composable("uploadHistory") { UploadHistoryScreen(navController) }
                        composable("myPrescriptions") { MyPrescriptionsScreen(navController) }
                        composable("dietPrecautions") { DietPrecautionsScreen(navController) }
                        composable("findDoctor") { FindDoctorScreen(navController) }
                        composable("bookAppointment") { BookAppointmentScreen(navController) }
                        composable("appointments") { AppointmentsScreen(navController) }
                        composable("notifications") { NotificationsScreen(navController) }
                        composable("myProfile") { MyProfileScreen(navController) }
                        composable("settings") { SettingsScreen(navController) }
                        composable("aboutApp") { AboutAppScreen(navController) }
                        composable("privacyPolicy") { PrivacyPolicyScreen(navController) }
                        composable("termsAndConditions") { TermsAndConditionsScreen(navController) }
                        composable("helpAndFaqs") { HelpAndFaqsScreen(navController) }
                        composable("chat") { ChatScreen(
                            navController,
                            doctorName = TODO()
                        ) }
                        // voice/video call screens removed: phone dialing will be used instead

                        // Doctor Section
                        composable("doctorLogin") { launchDoctorActivity() }
                    }
                }
            }
        }
    }

    private fun launchDoctorActivity() {
        val intent = Intent(this, DoctorActivity::class.java)
        startActivity(intent)
        finish()
    }
}
