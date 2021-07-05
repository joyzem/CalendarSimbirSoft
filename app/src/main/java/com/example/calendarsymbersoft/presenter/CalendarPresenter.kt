package com.example.calendarsymbersoft.presenter

import com.example.calendarsymbersoft.model.Event
import com.example.calendarsymbersoft.repository.MainRepository

class CalendarPresenter {
    private val repository = MainRepository()

    fun loadEventsBySelectedDate(day: Long): List<Event> {
        return repository.getEventsByDayId(day)
    }
}