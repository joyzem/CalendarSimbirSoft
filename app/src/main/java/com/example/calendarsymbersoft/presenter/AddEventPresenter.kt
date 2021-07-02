package com.example.calendarsymbersoft.presenter

import android.app.DatePickerDialog
import android.content.Context
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.model.TimeFormat
import java.util.*

class AddEventPresenter() : MainContract.AddEventPresenter {

    val timeFormat = TimeFormat
    var dayId: Long? = null
    var timeFrom: Long? = null
    var timeTo: Long? = null



}