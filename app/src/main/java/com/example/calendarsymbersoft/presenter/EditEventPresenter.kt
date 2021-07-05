package com.example.calendarsymbersoft.presenter

import com.example.calendarsymbersoft.model.Event
import com.example.calendarsymbersoft.repository.MainRepository

class EditEventPresenter {

    private val repository = MainRepository()
    private var eventID: Int? = null
    private var dayId: Long? = null
    private var timeFrom: Long? = null
    private var timeTo: Long? = null
    private var description: String? = null
    private var title: String? = null


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

    fun getEventTitle (): String {
        return repository
            .realm.where(Event::class.java).equalTo("id", eventID).findFirst()?.title!!
    }

    fun getEventDayId (): Long {
        return repository
            .realm.where(Event::class.java).equalTo("id", eventID).findFirst()?.dayId!!
    }

    fun getEventTimeFrom (): Long {
        return repository
            .realm.where(Event::class.java).equalTo("id", eventID).findFirst()?.timeFrom!!
    }

    fun getEventTimeTo (): Long {
        return repository
            .realm.where(Event::class.java).equalTo("id", eventID).findFirst()?.timeTo!!
    }

    fun getEventDescription (): String {
        return repository
            .realm.where(Event::class.java).equalTo("id", eventID).findFirst()?.description!!
    }

    fun updateEvent () {
        val event = Event(
            id = eventID,
            title = title,
            dayId = dayId,
            timeFrom = timeFrom,
            timeTo = timeTo,
            description = description
        )
        val eventJson = repository.gson.toJson(event)
        repository.addOrUpdateEvent(eventJson)
    }

    fun deleteEvent() {
        repository.deleteEvent(eventID!!)
    }

}