package com.example.calendarsymbersoft.presenter

import android.widget.CalendarView
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.model.Event
import com.example.calendarsymbersoft.repository.MainRepository

class CalendarPresenter(private val calView: MainContract.View) : MainContract.CalendarPresenter {

    private val repository = MainRepository()

    override fun addEventBtnWasClicked() {
        calView.moveToAnotherFragment()
    }

    override fun loadEventsBySelectedDate(day: Long): List<Event> {
        return repository.getEventsByDayId(day)
    }
}