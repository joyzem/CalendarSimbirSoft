package com.example.calendarsymbersoft.repository

import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.model.Event
import com.google.gson.Gson
import io.realm.Realm

class MainRepository : MainContract.Repository {

    val gson = Gson()
    val realm: Realm = Realm.getDefaultInstance()

    override fun getEventsByDayId(dayId: Long): List<Event>{
        return realm.where(Event::class.java).equalTo("dayId", dayId).findAll().toList()
    }

    override fun addOrUpdateEvent(jsonString: String) {
        realm.executeTransaction {
            realm.createOrUpdateObjectFromJson(Event::class.java, jsonString)
        }
    }

    fun findNextId(): Int {
        val currentIdNumber = realm.where(Event::class.java).max("id")
        return if (currentIdNumber == null) {
            1
        } else {
            currentIdNumber.toInt() + 1
        }
    }

    override fun deleteEvent(id: Int) {
        realm.executeTransaction {
            realm.where(Event::class.java).equalTo("id", id).findFirst()!!.deleteFromRealm()
        }
    }

}