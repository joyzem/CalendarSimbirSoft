package com.example.calendarsymbersoft.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Event(
    @PrimaryKey
    var id: Int? = null,
    var dayId: Long? = null,
    var title: String? = null,
    var timeFrom: Long? = null,
    var timeTo: Long? = null,
    var description: String? = null
) : EventInterface, RealmObject() {
    override fun getTimeRange(): String {
        return TimeFormat.timeFormat.format(timeFrom!!).toString() + " - " + TimeFormat
            .timeFormat.format(timeTo!!).toString()
    }
}