package com.example.calendarsymbersoft.presenter

import com.example.calendarsymbersoft.R
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.model.Event
import com.example.calendarsymbersoft.repository.MainRepository

class AddEventPresenter(private val addEventView: MainContract.View){

    private val repository = MainRepository()
    private var dayID: Long? = null
    private var eventTitle: String? = null
    private var eventTimeFrom: Long? = null
    private var eventTimeTo: Long? = null
    private var description: String? = null

    fun setDayID(day: Long) {
        dayID = day
    }

    fun setTimeFrom(time: Long) {
        eventTimeFrom = time
    }

    fun setTimeTo(time: Long) {
        eventTimeTo = time
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun setEventTitle(title: String) {
        eventTitle = title
    }

    fun getDayId() = dayID

    fun getTimeFrom() = eventTimeFrom

    fun getTimeTo() = eventTimeTo

    fun saveEventToDB(): String {
        try {
            val nextId = repository.findNextId()
            val event = Event (
                dayId = dayID,
                timeFrom = eventTimeFrom,
                timeTo = eventTimeTo,
                title = eventTitle,
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
}