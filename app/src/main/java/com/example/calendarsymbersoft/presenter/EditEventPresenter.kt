package com.example.calendarsymbersoft.presenter

import com.example.calendarsymbersoft.R
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.model.Event
import com.example.calendarsymbersoft.repository.MainRepository

class EditEventPresenter(private val mView: MainContract.View) {

    /**
     *  Presenter for EditEventFragment
     *  Contains logic related to Events' updating
     */

    private val repository = MainRepository()
    private var eventID: Int? = null
    private var dayId: Long? = null
    private var timeFrom: Long? = null
    private var timeTo: Long? = null
    private var description: String? = null
    private var title: String? = null

    // Initialization of the fields with the eventID
    fun fillAllByID() {
        dayId = getEventDayId()
        timeFrom = getEventTimeFrom()
        timeTo = getEventTimeTo()
        description = getEventDescription()
        title = getEventTitle()
    }

    fun setEventID(eventID: Int) {
        this.eventID = eventID
    }

    fun setDayID(dayID: Long) {
        dayId = dayID
    }

    fun setTimeFrom(time: Long) {
        timeFrom = time
    }

    fun setTimeTo(time: Long) {
        timeTo = time
    }

    fun setDescription(description: String) {
        this.description = description
    }

    fun setEventTitle(title: String) {
        this.title = title
    }

    // Checking for valid input
    // We have to check only that title is not empty
    // And timeFrom is less than timeTo
    // TextEdits: date, timeFrom, timeTo are not empty exactly
    fun validInput(title: String): Boolean {
        return timeFrom!! < timeTo!! && title.isNotEmpty()
    }

    // Taking values from the database
    fun getEventTitle (): String {
        return repository.getTitle(eventID!!)
    }

    fun getEventDayId (): Long {
        return repository.getDayId(eventID!!)
    }

    fun getEventTimeFrom (): Long {
        return repository.getTimeFrom(eventID!!)
    }

    fun getEventTimeTo (): Long {
        return repository.getTimeTo(eventID!!)
    }

    fun getEventDescription (): String {
        return repository.getDescription(eventID!!)
    }

    // Event's updating
    fun updateEvent () {
        val event = Event(
            id = eventID,
            title = title,
            dayId = dayId,
            timeFrom = timeFrom,
            timeTo = timeTo,
            description = description
        )
        if (event.validFields()) {
            val eventJson = repository.gson.toJson(event)
            repository.addOrUpdateEvent(eventJson)
            // Toast "Event updated successfully"
            mView.showMessage(mView.getStringResource(R.string.event_updated))
        } else {
            // Toast "Incorrect input"
            mView.showMessage(mView.getStringResource(R.string.incorrect_input))
        }
    }

    // Delete the event by eventID
    fun deleteEvent() {
        repository.deleteEvent(eventID!!)
    }

}