package com.example.calendarsymbersoft.view.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    /**
     * Fragment for adding new Events
     * Navigated from the CalendarFragment
     */

    // Presenter of the fragment
    private val presenter = AddEventPresenter(this)
    // Setting ViewBinding
    private var _binding: FragmentAddEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Binding listeners
        binding.dateEditText.setOnClickListener {
            pickDateByDialog()
        }

        binding.timeFromEditText.setOnClickListener {
            pickTimeByDialog(isTimeFrom = true)
        }

        binding.timeToEditText.setOnClickListener {
            pickTimeByDialog(isTimeFrom = false)
        }

        binding.commitBtn.setOnClickListener {
            commitBtnWasClicked()
        }

        binding.backBtn.setOnClickListener {
            navToAnotherFragment(R.id.action_addEventFragment_to_calendarFragment)
        }
    }

    private fun pickDateByDialog() {
        // Taking current date
        val now = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this.requireContext(),
            { _, year, month, dayOfMonth ->
                // On date selected listener
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                selectedDate.set(Calendar.HOUR_OF_DAY, 0)
                selectedDate.set(Calendar.MINUTE, 0)
                selectedDate.set(Calendar.SECOND, 0)
                selectedDate.set(Calendar.MILLISECOND, 0)
                // Set DayId into the presenter
                presenter.setDayID(selectedDate.time.time)
                // Formatting the date
                val dayTextFormat = TimeFormat.dateFormat.format(selectedDate.time.time)
                binding.dateEditText.setText(dayTextFormat)
            },
            now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun pickTimeByDialog(isTimeFrom: Boolean /* Necessary to define whether it's for
     timeFrom textEdit or timeTo textEdit*/){
        val now = Calendar.getInstance()
        val timePicker = TimePickerDialog(
            this.requireContext(),
            // On timeSetListener
            { _, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                if (isTimeFrom) {
                    // Setting selected time to the presenter
                    presenter.setTimeFrom(selectedTime.time.time)
                    binding.timeFromEditText.setText(TimeFormat.timeFormat.format(selectedTime.time.time))
                } else {
                    presenter.setTimeTo(selectedTime.time.time)
                    binding.timeToEditText.setText(TimeFormat.timeFormat.format(selectedTime.time.time))
                }
            },
            now.get(Calendar.HOUR_OF_DAY), 0, false
        )
        timePicker.show()
    }

    private fun commitBtnWasClicked() {
        try {
            // Checking for valid user input
            if (presenter.validFields(binding.titleEditText.toString())) {
                // Setting event's title to presenter
                presenter.setEventTitle(binding.titleEditText.text.toString())
                if (binding.descriptionEditText.text.isNullOrEmpty()) {
                    // Setting description via strings' resources
                    presenter.setDescription(getString(R.string.no_description))
                } else {
                    // ... Or entered description
                    presenter.setDescription(binding.descriptionEditText.text.toString())
                }
                // Saving after checking
                presenter.saveEventToDB()
                // Navigating to the CalendarFragment
                navToAnotherFragment(R.id.action_addEventFragment_to_calendarFragment)
            } else {
                // Toast "Incorrect input"
                showMessage(getString(R.string.incorrect_input))
            }
        } catch (e: Exception) {
            showMessage("Error: $e")
        }
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