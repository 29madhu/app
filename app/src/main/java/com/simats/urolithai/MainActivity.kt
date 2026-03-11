package com.simats.urolithai

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.simats.urolithai.ui.theme.UroLithAITheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            UroLithAITheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {

                        composable("login") {

                            LoginScreen(

                                onLoginSuccess = { role ->

                                    if (role == "Patient") {

                                        navController.navigate("dashboard") {
                                            popUpTo("login") { inclusive = true }
                                        }

                                    } else {

                                        launchDoctorActivity()

                                    }
                                },

                                onForgotPassword = {
                                    navController.navigate("forgotPassword")
                                }

                            )
                        }

                        composable("forgotPassword") {

                            ForgotPasswordScreen(

                                onNavigateBack = {
                                    navController.popBackStack()
                                },

                                onSendOtp = { email ->
                                    navController.navigate("otpVerification/$email")
                                }

                            )
                        }

                        composable(
                            route = "otpVerification/{email}",
                            arguments = listOf(
                                navArgument("email") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->

                            val email =
                                backStackEntry.arguments?.getString("email") ?: ""

                            OtpVerificationScreen(

                                email = email,

                                onNavigateBack = {
                                    navController.popBackStack()
                                },

                                onOtpVerified = { userId ->
                                    navController.navigate("accountCreated/$userId")
                                }

                            )
                        }

                        composable(
                            route = "accountCreated/{userId}",
                            arguments = listOf(
                                navArgument("userId") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->

                            val userId =
                                backStackEntry.arguments?.getString("userId") ?: ""

                            AccountCreatedScreen(

                                userId = userId,

                                onCreatePassword = {
                                    navController.navigate("login")
                                }

                            )
                        }

                        composable("dashboard") {
                            DashboardScreen(navController)
                        }

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