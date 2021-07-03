package com.example.calendarsymbersoft.view.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.calendarsymbersoft.R
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.databinding.FragmentAddEventBinding
import com.example.calendarsymbersoft.model.TimeFormat
import com.example.calendarsymbersoft.presenter.AddEventPresenter
import com.example.calendarsymbersoft.repository.MainRepository
import java.util.*

class AddEventFragment : Fragment(), MainContract.AddEventView {

    private val presenter = AddEventPresenter()
    private var _binding: FragmentAddEventBinding? = null
    private val binding get() = _binding!!
    private var _dayId: Long? = null
    private var _timeFrom: Long? = null
    private var _timeTo: Long? = null
    private val dayId get() = _dayId!!
    private val timeFrom get() = _timeFrom!!
    private val timeTo get() = _timeTo!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.dateEditText.setOnClickListener {
            _dayId = pickDateByDialog()
        }

        binding.timeFromEditText.setOnClickListener {
            _timeFrom = pickTimeByDialog(binding.timeFromEditText)
        }

        binding.timeToEditText.setOnClickListener {
            _timeTo = pickTimeByDialog(binding.timeToEditText)
        }

        binding.commitBtn.setOnClickListener {
            if (validFields()) {

                presenter.saveEventToDB(
                    dayId = dayId,
                    timeFrom = timeFrom,
                    timeTo = timeTo,
                    description = binding.descriptionEditText.text.toString(),
                    title = binding.titleEditText.text.toString()
                )
            } else {
                Toast.makeText(this.requireContext(), R.string.incorrect_input, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validFields(): Boolean {
        if (binding.titleEditText.text.isNullOrEmpty()
            || binding.dateEditText.text.isNullOrEmpty()
            || binding.timeFromEditText.text.isNullOrEmpty()
            || binding.timeToEditText.text.isNullOrEmpty()) {
            return true
        }
        else {
            return false
        }
    }

    private fun pickDateByDialog(): Long {
        val now = Calendar.getInstance()
        var dayId: Long = now.time.time
        val datePicker = DatePickerDialog(
            this.requireContext(),
            { view, year, month, dayOfMonth ->

                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                selectedDate.set(Calendar.HOUR_OF_DAY, 0)
                selectedDate.set(Calendar.MINUTE, 0)
                selectedDate.set(Calendar.SECOND, 0)
                selectedDate.set(Calendar.MILLISECOND, 0)

                dayId = selectedDate.time.time

                val dayTextFormat = TimeFormat.dateFormat.format(dayId)
                binding.dateEditText.setText(dayTextFormat)
            },
            now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        )

        datePicker.show()

        return dayId
    }

    private fun pickTimeByDialog(textField: EditText): Long {
        val now = Calendar.getInstance()
        var time: Long = now.time.time
        val timePicker = TimePickerDialog(
            this.requireContext(), TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()

                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                time = selectedTime.time.time
                textField.setText(TimeFormat.timeFormat.format(selectedTime.time))

            },
            now.get(Calendar.HOUR_OF_DAY), 0, false
        )
        timePicker.show()
        return time
    }

}