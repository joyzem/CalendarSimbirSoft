package com.example.calendarsymbersoft.presenter

import android.app.DatePickerDialog
import android.content.Context
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.model.TimeFormat
import com.example.calendarsymbersoft.repository.MainRepository
import java.util.*

class AddEventPresenter() : MainContract.AddEventPresenter {

    private val repository = MainRepository()

    fun saveEventToDB(
        dayId: Long,
        timeFrom: Long,
        timeTo: Long,
        title: String,
        description: String
    ) {
        TODO("Not yet implemented")
    }

}