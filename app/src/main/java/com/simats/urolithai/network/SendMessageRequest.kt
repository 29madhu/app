package com.simats.urolithai.network

data class SendMessageRequest(
    val message: String,
    val sender_id: Int
)