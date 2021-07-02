package com.example.calendarsymbersoft.model

import java.text.SimpleDateFormat
import java.util.*

object TimeFormat {
    val dateFormat = SimpleDateFormat("dd MMM, YYYY", Locale.ENGLISH)
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
}