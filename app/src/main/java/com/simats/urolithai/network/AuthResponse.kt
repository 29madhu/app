package com.simats.urolithai.network

data class AuthResponse(
    val access: String,
    val refresh: String,
    val role: String
)