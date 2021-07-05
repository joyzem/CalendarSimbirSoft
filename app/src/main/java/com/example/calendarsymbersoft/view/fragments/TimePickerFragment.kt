package com.example.calendarsymbersoft.view.fragments

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.example.calendarsymbersoft.contract.MainContract
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    private lateinit var calendar: Calendar
    private var time: Long? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        calendar = Calendar.getInstance()

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = 0

        return TimePickerDialog(
            activity,
            this,
            hour,
            minute,
            false
        )
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val selectedTime = Calendar.getInstance()
        selectedTime.set(Calendar.HOUR, hourOfDay)
        selectedTime.set(Calendar.MINUTE, minute)
        time = selectedTime.time.time
    }

    fun getTime() = time
}