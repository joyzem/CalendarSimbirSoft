package com.example.calendarsymbersoft.contract

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.calendarsymbersoft.model.Event

interface MainContract {

    interface View {
        fun getStringResource(resourceId: Int): String
        fun navToAnotherFragment(resourceId: Int)
        fun navToAnotherFragment(resourceId: Int, args: Bundle)
        fun showMessage(message: String)
    }

    interface AddEventPresenter {
        fun backBtnWasClicked()
        fun saveEventToDB(
            dayId: Long,
            timeFrom: Long,
            timeTo: Long,
            title: String,
            description: String
        ): String
    }

    interface CalendarPresenter {
        fun loadEventsBySelectedDate(day: Long): List<Event>
    }

    interface Repository {
        fun getEventsByDayId(dayId: Long): List<Event>
        fun addOrUpdateEvent(jsonString: String)
        fun deleteEvent(id: Int, dayId: Long)
    }

}