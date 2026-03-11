package com.simats.urolithai.network

data class SetPasswordRequest(
    val userId: String,
    val password: String
)