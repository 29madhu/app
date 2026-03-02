package com.simats.urolithai

data class Case(val id: String, val title: String, val type: String, val date: String, val status: String)

val allCases = listOf(
    Case("RPT-001", "Ultrasound Left Kidney", "Ultrasound", "12 Jan 2025", "Approved"),
    Case("RPT-002", "CT Scan Bilateral", "CT Scan", "08 Jan 2025", "Approved"),
    Case("RPT-004", "Ultrasound Follow-up", "Ultrasound", "15 Jan 2025", "Approved"),
    Case("RPT-003", "KUB X-Ray", "X-Ray", "14 Jan 2025", "Pending"),
    Case("RPT-005", "MRI Scan", "MRI", "16 Jan 2025", "Rejected"),
    Case("RPT-006", "Ultrasound Follow-up 2", "Ultrasound", "18 Jan 2025", "Follow-up")
)

data class Upload(
    val id: String,
    val name: String,
    val date: String,
    val doctor: String,
    val status: String,
    val icon: Int
)

val uploads = listOf(
    Upload("rpt-001", "Ultrasound Left Kidney", "12 Jan 2025", "Dr. Priya Sharma", "pending", R.drawable.timer),
    Upload("rpt-002", "CT Scan Bilateral", "08 Jan 2025", "Dr. Priya Sharma", "approved", R.drawable.right),
    Upload("rpt-003", "X-Ray KUB", "01 Jan 2025", "Dr. Amit Patel", "rejected", R.drawable.wrong),
    Upload("rpt-004", "Ultrasound Follow-up", "15 Jan 2025", "Dr. Priya Sharma", "pending", R.drawable.timer)
)


data class ReportDetails(
    val id: String,
    val name: String,
    val date: String,
    val doctor: String,
    val status: String,
    val reportType: String,
    val aiAnalysis: AiAnalysis,
    val doctorNotes: String
)

data class AiAnalysis(
    val stoneType: String,
    val size: String,
    val location: String
)

val reportDetailsData = ReportDetails(
    id = "RPT-002",
    name = "CT Scan Bilateral",
    date = "08 Jan 2025",
    doctor = "Dr. Priya Sharma",
    status = "approved",
    reportType = "CT Scan",
    aiAnalysis = AiAnalysis(
        stoneType = "Calcium\nOxalate",
        size = "6.2mm",
        location = "Left Kidney Lower Pole"
    ),
    doctorNotes = "Stone size is moderate. Immediate surgery is not required. Prescribed medication to help dissolve/pass the stone. Increase fluid intake significantly. Follow up in 2 weeks."
)
