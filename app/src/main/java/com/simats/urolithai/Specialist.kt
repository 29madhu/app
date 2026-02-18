package com.simats.urolithai

data class Specialist(
    val initials: String,
    val name: String,
    val specialization: String,
    val hospital: String,
    val experience: Int,
    val rating: Double,
    val fee: Int,
    val location: String,
    val tags: List<String>
)

val specialists = listOf(
    Specialist(
        initials = "RK",
        name = "Dr. Rajesh Kumar",
        specialization = "Urologist",
        hospital = "Apollo Hospitals",
        experience = 15,
        rating = 4.9,
        fee = 1200,
        location = "Bangalore",
        tags = listOf("Kidney Stones", "Prostate Care", "Urology Consultation")
    ),
    Specialist(
        initials = "PS",
        name = "Dr. Priya Sharma",
        specialization = "Urologist",
        hospital = "Fortis Hospital",
        experience = 12,
        rating = 4.8,
        fee = 1000,
        location = "Mumbai",
        tags = listOf("Urinary Tract Infections", "Kidney Stones", "Male Infertility")
    )
)
