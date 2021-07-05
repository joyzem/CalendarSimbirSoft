package com.example.calendarsymbersoft.view.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.calendarsymbersoft.R
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.databinding.FragmentAddEventBinding
import com.example.calendarsymbersoft.model.TimeFormat
import com.example.calendarsymbersoft.presenter.AddEventPresenter
import java.lang.Exception
import java.util.*

class AddEventFragment : Fragment(), MainContract.View {

    private val presenter = AddEventPresenter(this)
    private var _binding: FragmentAddEventBinding? = null
    private val binding get() = _binding!!
    private var dayId: Long? = null
    private var timeFrom: Long? = null
    private var timeTo: Long? = null

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
            pickDateByDialog()
        }

        binding.timeFromEditText.setOnClickListener {
            pickTimeFromByDialog()
        }

        binding.timeToEditText.setOnClickListener {
            pickTimeToByDialog()
        }

        binding.commitBtn.setOnClickListener {
            try {
                if (validFields()) {
                    if (binding.descriptionEditText.text.isNullOrEmpty()) {
                        binding.descriptionEditText.setText(R.string.no_description)
                    }
                    val resultOfSaving = presenter.saveEventToDB(
                        dayId = dayId!!,
                        timeFrom = timeFrom!!,
                        timeTo = timeTo!!,
                        description = binding.descriptionEditText.text.toString(),
                        title = binding.titleEditText.text.toString()
                    )
                    Toast.makeText(this.requireContext(), resultOfSaving, Toast.LENGTH_SHORT).show()
                    navToAnotherFragment(R.id.action_addEventFragment_to_calendarFragment)
                } else {
                    Toast.makeText(this.requireContext(), R.string.incorrect_input, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this.requireContext(), "Error: $e", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backBtn.setOnClickListener {
            navToAnotherFragment(R.id.action_addEventFragment_to_calendarFragment)
        }
    }

    private fun validFields(): Boolean {
        if (!(binding.titleEditText.text.toString().isEmpty() ||
                    binding.dateEditText.text.toString().isEmpty() ||
                    binding.timeFromEditText.text.toString().isEmpty() ||
                    binding.timeToEditText.text.toString().isEmpty()) &&
                    dayId != null &&
                    timeFrom != null &&
                    timeTo != null &&
                    timeFrom!! < timeTo!!) {
            return true
        } else {
            return false
        }
    }

    private fun pickDateByDialog() {
        val now = Calendar.getInstance()
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
    }

    private fun pickTimeToByDialog(){
        val now = Calendar.getInstance()
        val timePicker = TimePickerDialog(
            this.requireContext(), { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                timeTo = selectedTime.time.time
                binding.timeToEditText.setText(TimeFormat.timeFormat.format(timeTo))
            },
            now.get(Calendar.HOUR_OF_DAY), 0, false
        )
        timePicker.show()
    }

    private fun pickTimeFromByDialog(){
        val now = Calendar.getInstance()
        val timePicker = TimePickerDialog(
            this.requireContext(), TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                timeFrom = selectedTime.time.time
                binding.timeFromEditText.setText(TimeFormat.timeFormat.format(timeFrom))
            },
            now.get(Calendar.HOUR_OF_DAY), 0, false
        )
        timePicker.onClick(timePicker, DialogInterface.BUTTON_POSITIVE) {

        }
        timePicker.show()

    }

    override fun getStringResource(resourceId: Int): String {
        return this.getString(resourceId)
    }

    override fun navToAnotherFragment(resourceId: Int) {
        findNavController().navigate(resourceId)
    }

    override fun navToAnotherFragment(resourceId: Int, args: Bundle) {
        findNavController().navigate(resourceId, args)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this.requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}