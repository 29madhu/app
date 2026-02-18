package com.simats.urolithai

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object RoleSelection : Screen("role_selection")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object Home : Screen("home")
    object Reports : Screen("reports")
    object Appointments : Screen("appointments")
    object Settings : Screen("settings")
    object Chat : Screen("chat/{doctorName}") {
        fun createRoute(doctorName: String) = "chat/$doctorName"
    }
    object VoiceCall : Screen("voice_call/{doctorName}") {
        fun createRoute(doctorName: String) = "voice_call/$doctorName"
    }
    object VideoCall : Screen("video_call/{doctorName}") {
        fun createRoute(doctorName: String) = "video_call/$doctorName"
    }
    object BookAppointment : Screen("book_appointment")
    object DoctorDetails : Screen("doctor_details/{doctorName}") {
        fun createRoute(doctorName: String) = "doctor_details/$doctorName"
    }
    object MyReports : Screen("my_reports")
    object ReportDetails : Screen("report_details/{uploadId}") {
        fun createRoute(uploadId: String) = "report_details/$uploadId"
    }
    object ReportSent : Screen("report_sent/{doctorName}") {
        fun createRoute(doctorName: String) = "report_sent/$doctorName"
    }
    object DoctorProfile : Screen("doctor_profile/{specialistInitials}") {
        fun createRoute(specialistInitials: String) = "doctor_profile/$specialistInitials"
    }
    object RateExperience : Screen("rate_experience/{specialistInitials}") {
        fun createRoute(specialistInitials: String) = "rate_experience/$specialistInitials"
    }
    object Notifications : Screen("notifications")
    object FindSpecialist : Screen("find_specialist")
    object UploadHistory : Screen("upload_history")
    object DietPrecautions : Screen("diet_precautions")
    object MyPrescriptions : Screen("my_prescriptions")
    object ShareReport : Screen("share_report")
    object AccountCreated : Screen("account_created/{role}") {
        fun createRoute(role: String) = "account_created/$role"
    }
    object PasswordResetSuccess : Screen("password_reset_success")
    object CreateAccount : Screen("create_account/{role}") {
        fun createRoute(role: String) = "create_account/$role"
    }
    object OtpVerification : Screen("otp_verification/{role}") {
        fun createRoute(role: String) = "otp_verification/$role"
    }
}
