package com.example.calendarsymbersoft.presenter

import com.example.calendarsymbersoft.model.Event
import com.example.calendarsymbersoft.repository.MainRepository

class EditEventPresenter {

    private val repository = MainRepository()

    fun getTitleByID (id: Int): String {
        return repository
            .realm.where(Event::class.java).equalTo("id", id).findFirst()?.title!!
    }

    fun getDayIdByID (id: Int): Long {
        return repository
            .realm.where(Event::class.java).equalTo("id", id).findFirst()?.dayId!!
    }

    fun getTimeFromByID (id: Int): Long {
        return repository
            .realm.where(Event::class.java).equalTo("id", id).findFirst()?.timeFrom!!
    }

    fun getTimeToByID (id: Int): Long {
        return repository
            .realm.where(Event::class.java).equalTo("id", id).findFirst()?.timeTo!!
    }

    fun getDescriptionById (id: Int): String {
        return repository
            .realm.where(Event::class.java).equalTo("id", id).findFirst()?.description!!
    }

    fun updateEvent (
        id: Int,
        title: String,
        dayID: Long,
        timeFrom: Long,
        timeTo: Long,
        description: String
    ) {
        val event = Event(
            id = id,
            title = title,
            dayId = dayID,
            timeFrom = timeFrom,
            timeTo = timeTo,
            description = description
        )
        val eventJson = repository.gson.toJson(event)
        repository.addOrUpdateEvent(eventJson)
    }

    fun deleteEvent(id: Int) {
        repository.deleteEventById(id)
    }

}