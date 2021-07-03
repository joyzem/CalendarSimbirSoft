package com.example.calendarsymbersoft.contract

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.calendarsymbersoft.model.Event

interface MainContract {

    interface View {
        fun moveToAnotherFragment()
        fun getStringResource(resourceId: Int): String
    }

    interface AddEventPresenter {

    }

    interface CalendarPresenter {
        fun addEventBtnWasClicked()
        fun loadEventsBySelectedDate(view: android.widget.CalendarView): List<Event>
    }

    interface Repository {
        fun loadEvents(dayId: Long): List<Event>
        fun saveEvent(jsonString: String)
    }

}