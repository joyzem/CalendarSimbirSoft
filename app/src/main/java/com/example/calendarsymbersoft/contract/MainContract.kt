package com.example.calendarsymbersoft.contract

import android.view.View
import android.widget.CalendarView
import com.example.calendarsymbersoft.model.Event

interface MainContract {
    interface AddEventView {

    }

    interface CalendarView {

    }

    interface AddEventPresenter {

    }

    interface CalendarPresenter {
        fun addEventBtnWasClicked(view: View)
        fun loadEventsBySelectedDate(view: android.widget.CalendarView): List<Event>
    }

    interface Repository {

    }

}