package com.simats.urolithai

import androidx.compose.runtime.mutableStateListOf

object ChatRepository {
    val messages = mutableStateListOf(
        ChatMessage("Hello Dr. Sharma, I've uploaded my ultrasound report.", "10:30 AM", false),
        ChatMessage("Hi Rajesh. I see it. The stone size is 8.2mm.", "10:32 AM", true, true),
        ChatMessage("Is surgery required?", "10:33 AM", false),
        ChatMessage("Not immediately. We can try medication first since it's <7mm. I've prescribed Potassium Citrate.", "10:35 AM", true, true)
    )

    fun addMessage(message: ChatMessage) {
        messages.add(message)
    }
}
