package com.example.calendarsymbersoft.model

class Event(
    var id: Int? = null,
    var dayId: Long? = null,
    var title: String? = null,
    var timeFrom: Long? = null,
    var timeTo: Long? = null,
    val description: String? = null
) : EventInterface {
    override fun getTimeRange(): String {
//        TODO("Not yet implemented")
        return ""
    }
}