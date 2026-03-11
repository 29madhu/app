package com.simats.urolithai.network

data class VerifyOtpRequest(
    val email: String,
    val otp: String
)