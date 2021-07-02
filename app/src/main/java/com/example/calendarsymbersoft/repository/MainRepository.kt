package com.example.calendarsymbersoft.repository

import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.model.Event

class MainRepository : MainContract.Repository {
    fun loadEvents(dayId: Long): List<Event>{
        return listOf(
            Event(
                id = 1,
                dayId = 1,
                title = "SJK",
                timeFrom = 1000000,
                timeTo = 1000010,
                description = "Description"),
            Event(
                id = 2,
                dayId = 2,
                title = "LJK",
                timeFrom = 1000000,
                timeTo = 1000010,
                description = "Description 2")
        )
    }
}