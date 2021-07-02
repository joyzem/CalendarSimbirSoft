package com.example.calendarsymbersoft.presenter

import android.view.View
import android.widget.CalendarView
import androidx.navigation.findNavController
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.model.Event
import com.example.calendarsymbersoft.repository.MainRepository
import com.example.calendarsymbersoft.view.fragments.CalendarFragmentDirections

class CalendarPresenter : MainContract.CalendarPresenter {

    private val repository = MainRepository()

    override fun addEventBtnWasClicked(view: View) {
        val action = CalendarFragmentDirections
            .actionCalendarFragmentToAddEventFragment()
        view.findNavController().navigate(action)
    }

    override fun loadEventsBySelectedDate(view: CalendarView): List<Event> {
        return repository.loadEvents(view.date)
    }
}