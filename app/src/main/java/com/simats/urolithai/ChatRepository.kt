package com.simats.urolithai

import androidx.compose.runtime.mutableStateListOf

data class ChatMessage(
    val message: String,
    val time: String,
    val sender: String,
    val isRead: Boolean = false
)

object ChatRepository {

    val messages = mutableStateListOf(
        ChatMessage(
            "Hello Dr. Sharma, I've uploaded my ultrasound report.",
            "10:30 AM",
            "patient"
        ),
        ChatMessage(
            "Hi Rajesh. I see it. The stone size is 6.2mm.",
            "10:32 AM",
            "doctor",
            true
        ),
        ChatMessage(
            "Is surgery required?",
            "10:33 AM",
            "patient"
        ),
        ChatMessage(
            "Not immediately. We can try medication first since it's <7mm.",
            "10:35 AM",
            "doctor",
            true
        )
    )

    fun addMessage(message: ChatMessage) {
        messages.add(message)
    }
}