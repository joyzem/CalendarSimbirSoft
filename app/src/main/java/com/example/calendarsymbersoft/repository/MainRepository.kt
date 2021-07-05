package com.example.calendarsymbersoft.repository

import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.model.Event
import com.google.gson.Gson
import io.realm.Realm

class MainRepository : MainContract.Repository {

    // Gson for converting to JSON strings
    val gson = Gson()
    // realm as the Database
    val realm: Realm = Realm.getDefaultInstance()

    // Find all events where dayId is equal to param
    override fun getEventsByDayId(dayId: Long): List<Event>{
        return realm.where(Event::class.java).equalTo("dayId", dayId).findAll().toList()
    }

    // Adding or updating of event by using jsonString object
    override fun addOrUpdateEvent(jsonString: String) {
        realm.executeTransaction {
            realm.createOrUpdateObjectFromJson(Event::class.java, jsonString)
        }
    }

    // Find Id for the next Event
    fun findNextId(): Int {
        val currentIdNumber = realm.where(Event::class.java).max("id")
        return if (currentIdNumber == null) {
            1
        } else {
            currentIdNumber.toInt() + 1
        }
    }

    // Delete event by ID
    override fun deleteEvent(id: Int) {
        realm.executeTransaction {
            realm.where(Event::class.java).equalTo("id", id).findFirst()!!.deleteFromRealm()
        }
    }

    fun getDescription(id: Int): String {
        return realm.where(Event::class.java).equalTo("id", id).findFirst()!!.description!!
    }

    fun getTimeTo(id: Int): Long {
        return realm.where(Event::class.java).equalTo("id", id).findFirst()!!.timeTo!!
    }

    fun getTimeFrom(id: Int): Long {
        return realm.where(Event::class.java).equalTo("id", id).findFirst()!!.timeFrom!!
    }

    fun getTitle(id: Int): String {
        return realm.where(Event::class.java).equalTo("id", id).findFirst()!!.title!!
    }

    fun getDayId(id: Int): Long {
        return realm.where(Event::class.java).equalTo("id", id).findFirst()!!.dayId!!
    }
}