package com.example.calendarsymbersoft.presenter

import com.example.calendarsymbersoft.R
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.model.Event
import com.example.calendarsymbersoft.repository.MainRepository

class AddEventPresenter(private val addEventView: MainContract.View) : MainContract.AddEventPresenter {

    private val repository = MainRepository()

    override fun saveEventToDB(
        dayId: Long,
        timeFrom: Long,
        timeTo: Long,
        title: String,
        description: String
    ): String {
        try {
            val nextId = repository.findNextId()
            val event = Event (
                dayId = dayId,
                timeFrom = timeFrom,
                timeTo = timeTo,
                title = title,
                description = description,
                id = nextId
            )

            val eventJson = repository.gson.toJson(event)
            repository.addOrUpdateEvent(eventJson)

            return addEventView.getStringResource(R.string.event_added_successfully)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    override fun backBtnWasClicked() {
        addEventView.navToAnotherFragment(R.id.action_addEventFragment_to_calendarFragment)
    }

}