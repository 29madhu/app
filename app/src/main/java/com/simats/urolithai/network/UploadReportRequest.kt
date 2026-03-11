package com.simats.urolithai.network

data class UploadReportRequest(
    val patientId: Int,
    val imageUrl: String
)