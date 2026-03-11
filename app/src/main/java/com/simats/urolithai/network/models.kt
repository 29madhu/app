package com.simats.urolithai.network

data class LoginRequest(
    val email: String,
    val password: String
)


data class ReportSummary(
    val id: Int,
    val stoneType: String,
    val uploadDate: String,
    val status: String
)

data class PatientDashboardResponse(
    val id: Int,
    val name: String,
    val totalReports: Int,
    val recentReports: List<ReportSummary>
)

data class DoctorDashboardResponse(
    val pendingReviews: Int,
    val totalPatients: Int
)