package com.example.calendarsymbersoft.model

import java.text.SimpleDateFormat
import java.util.*

object TimeFormat {
    // Object for formating date as Long and timestamps as Long
    val dateFormat = SimpleDateFormat("dd MMM, YYYY", Locale.ENGLISH)
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
}