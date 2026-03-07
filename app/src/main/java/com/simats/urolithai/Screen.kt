package com.simats.urolithai

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Signup : Screen("signup")
    object ForgotPassword : Screen("forgotPassword")
    object Home : Screen("dashboard") // Alias for dashboard if needed
    object Dashboard : Screen("dashboard")
    object MyReports : Screen("myReports")
    object ReportDetails : Screen("reportDetails/{uploadId}") {
        fun createRoute(uploadId: String) = "reportDetails/$uploadId"
    }
    object ReportSent : Screen("reportSent/{doctorName}") {
        fun createRoute(doctorName: String) = "reportSent/$doctorName"
    }
    object UploadReport : Screen("uploadReport")
    object UploadHistory : Screen("uploadHistory")
    object MyPrescriptions : Screen("myPrescriptions")
    object DietPrecautions : Screen("dietPrecautions")
    object FindDoctor : Screen("findDoctor")
    object DoctorProfile : Screen("doctorProfile/{doctorName}") {
        fun createRoute(doctorName: String) = "doctorProfile/$doctorName"
    }
    object BookAppointment : Screen("bookAppointment")
    object Appointments : Screen("appointments")
    object Notifications : Screen("notifications")
    object MyProfile : Screen("myProfile")
    object Settings : Screen("settings")
    object Chat : Screen("chat?doctorName={doctorName}") {
        fun createRoute(doctorName: String) = "chat?doctorName=$doctorName"
    }
    object VoiceCall : Screen("voice_call/{doctorName}") {
        fun createRoute(doctorName: String) = "voice_call/$doctorName"
    }
    object VideoCall : Screen("video_call/{doctorName}") {
        fun createRoute(doctorName: String) = "video_call/$doctorName"
    }
    object ShareReport : Screen("shareReport")
    object RateExperience : Screen("rateExperience/{doctorName}") {
        fun createRoute(doctorName: String) = "rateExperience/$doctorName"
    }
    object AboutApp : Screen("aboutApp")
    object PrivacyPolicy : Screen("privacyPolicy")
    object TermsAndConditions : Screen("termsAndConditions")
    object HelpAndFaqs : Screen("helpAndFaqs")
    object DoctorPrivacyPolicy : Screen("doctorPrivacyPolicy")
}
