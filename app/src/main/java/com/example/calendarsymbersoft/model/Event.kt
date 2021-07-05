package com.example.calendarsymbersoft.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Event(

    /**
     * The input is not valid if...
     * ...dayId is negative
     * ...title is empty
     * ...timeFrom > timeTo
     * ...timeFrom < 0
     */

    @PrimaryKey
    var id: Int? = null,
    var dayId: Long? = null,
    var title: String? = null,
    var timeFrom: Long? = null,
    var timeTo: Long? = null,
    var description: String? = null
) : RealmObject() {

    fun getTimeRange(): String {
        return TimeFormat.timeFormat.format(timeFrom!!).toString() + " - " + TimeFormat
            .timeFormat.format(timeTo!!).toString()
    }

    fun validFields() : Boolean {
        if (dayId!! < 0) { return false }
        if (title!!.isEmpty()) { return false }
        if (timeFrom!! > timeTo!!) { return false }
        if (timeFrom!! < 0) { return false }
        return true
    }
}