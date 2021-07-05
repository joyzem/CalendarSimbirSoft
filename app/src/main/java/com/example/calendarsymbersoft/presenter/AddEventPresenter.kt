package com.example.calendarsymbersoft.presenter

import com.example.calendarsymbersoft.R
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.model.Event
import com.example.calendarsymbersoft.repository.MainRepository

class AddEventPresenter(private val addEventView: MainContract.View){

    /**
     * Presenter for AddEventFragment
     * Contains logic related to adding of Events...
     */

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

    // Validation of fields of Event class
    fun validFields(title: String) : Boolean {
        return title.isNotEmpty() &&
                dayID != null &&
                eventTimeFrom != null &&
                eventTimeTo != null &&
                eventTimeFrom!! < eventTimeTo!!
    }

    // Saving of Event to the Database
    fun saveEventToDB() {
        try {
            // Finding next ID for the new Event
            val nextId = repository.findNextId()
            // Creating instance of Event class
            val event = Event (
                dayId = dayID,
                timeFrom = eventTimeFrom,
                timeTo = eventTimeTo,
                title = eventTitle,
                description = description,
                id = nextId
            )
            // Checking for validality
            if (event.validFields()) {
                val eventJson = repository.gson.toJson(event)
                // Saving with Json
                repository.addOrUpdateEvent(eventJson)
                // Toast message "Event added successfully"
                addEventView.showMessage(addEventView.getStringResource(R.string.event_added_successfully))
            } else {
                // Toast message "Incorrect input"
                addEventView.showMessage(addEventView.getStringResource(R.string.incorrect_input))
            }
        } catch (e: Exception) {
            addEventView.showMessage(e.toString())
        }
    }
}