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

    override fun loadEvents(dayId: Long): List<Event>{
        return listOf(
            Event(
                dayId = 1,
                title = "SJK",
                timeFrom = 1000000,
                timeTo = 1000010,
                description = "Description",
                id = 1),
            Event(
                dayId = 2,
                title = "LJK",
                timeFrom = 1000000,
                timeTo = 1000010,
                description = "Description 2",
                id = 2)
        )
    }

    override fun saveEvent(jsonString: String) {
        realm.executeTransaction {
            realm.createOrUpdateObjectFromJson(Event::class.java, jsonString)
        }

        Log.d(TAG, "Success")
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

}