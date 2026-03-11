package com.simats.urolithai.network

data class ChatMessage(
    val content: String,
    val timestamp: String,
    val sender_id: Int
)