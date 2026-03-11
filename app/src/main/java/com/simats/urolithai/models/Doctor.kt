package com.simats.urolithai.models

data class Doctor(
    val initials: String,
    val name: String,
    val specialization: String,
    val hospital: String,
    val location: String,
    val rating: Double,
    val experience: Int,
    val degree: String,
    val fee: Int,
    val tags: List<String>
)