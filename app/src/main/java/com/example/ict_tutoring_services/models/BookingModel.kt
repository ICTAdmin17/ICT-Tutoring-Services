package com.example.ict_tutoring_services.models


data class BookingModel(
    val tutorUid:String? = null,
    val tutorName:String? = null,
    val tutorEmail:String? = null,
    val tutorModule:String? = null,
    val studentPhoneNumber:String? = null,
    val studentUid:String? = null,
    val studentEmail:String? = null,
    val time:String? = null,
    val date:String? = null,
    val bookingStatus:String? = null,
    val itemKey:String? = null
)