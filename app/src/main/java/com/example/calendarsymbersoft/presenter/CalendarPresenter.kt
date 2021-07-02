package com.example.calendarsymbersoft.presenter

import android.view.View
import androidx.navigation.findNavController
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.repository.MainRepository
import com.example.calendarsymbersoft.view.fragments.CalendarFragmentDirections

class CalendarPresenter(private val calendarView: MainContract.CalendarView) : MainContract.CalendarPresenter {

    private val repository = MainRepository()

    fun addEventBtnWasClicked(view: View) {
        val action = CalendarFragmentDirections
            .actionCalendarFragmentToAddEventFragment()
        view.findNavController().navigate(action)
    }

}