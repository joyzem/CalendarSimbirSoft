package com.example.calendarsymbersoft.repository

import android.util.Log
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.model.Event
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.realm.Realm

class MainRepository : MainContract.Repository {

    val TAG = "MainRepository"
    val gson = Gson()
    val realm = Realm.getDefaultInstance()

    override fun getEventsByDayId(dayId: Long): List<Event>{
        return realm.where(Event::class.java).equalTo("dayId", dayId).findAll().toList()
    }

    override fun addOrUpdateEvent(jsonString: String) {
        realm.executeTransaction {
            realm.createOrUpdateObjectFromJson(Event::class.java, jsonString)
        }
    }

    override fun deleteEvent(id: Int, dayId: Long) {
        TODO("Not yet implemented")
    }

    fun findNextId(): Int {
        val currentIdNumber = realm.where(Event::class.java).max("id")
        val nextId = if (currentIdNumber == null) {
            1
        } else {
            currentIdNumber.toInt() + 1
        }
        Log.d(TAG, nextId.toString())
        return nextId
    }

    fun deleteEventById(id: Int) {
        realm.executeTransaction {
            realm.where(Event::class.java).equalTo("id", id).findFirst()!!.deleteFromRealm()
        }
    }

}