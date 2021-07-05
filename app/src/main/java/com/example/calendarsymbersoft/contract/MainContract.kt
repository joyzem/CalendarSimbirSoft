package com.example.calendarsymbersoft.contract

import android.os.Bundle
import com.example.calendarsymbersoft.model.Event

interface MainContract {

    // interfaces for Fragments and Repository
    interface View {
        fun getStringResource(resourceId: Int): String
        fun navToAnotherFragment(resourceId: Int)
        fun navToAnotherFragment(resourceId: Int, args: Bundle)
        fun showMessage(message: String)
    }

    interface Repository {
        fun getEventsByDayId(dayId: Long): List<Event>
        fun addOrUpdateEvent(jsonString: String)
        fun deleteEvent(id: Int)
    }

}