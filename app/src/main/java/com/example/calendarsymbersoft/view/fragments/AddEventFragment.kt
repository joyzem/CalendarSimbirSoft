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

    private val presenter = AddEventPresenter(this)
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
            try {
                if (validFields()) {
                    presenter.setEventTitle(binding.titleEditText.text.toString())
                    if (binding.descriptionEditText.text.isNullOrEmpty()) {
                        presenter.setDescription(getString(R.string.no_description))
                    } else {
                        presenter.setDescription(binding.descriptionEditText.text.toString())
                    }
                    val resultOfSaving = presenter.saveEventToDB()
                    showMessage(resultOfSaving)
                    navToAnotherFragment(R.id.action_addEventFragment_to_calendarFragment)
                } else {
                    showMessage(getString(R.string.incorrect_input))
                }
            } catch (e: Exception) {
                showMessage("Error: $e")
            }
        }

        binding.backBtn.setOnClickListener {
            navToAnotherFragment(R.id.action_addEventFragment_to_calendarFragment)
        }
    }

    private fun validFields(): Boolean {
        return binding.titleEditText.text.toString().isNotEmpty() &&
                presenter.getDayId() != null &&
                presenter.getTimeFrom() != null &&
                presenter.getTimeTo() != null &&
                presenter.getTimeFrom()!! < presenter.getTimeTo()!!
    }

    private fun pickDateByDialog() {
        val now = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this.requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                selectedDate.set(Calendar.HOUR_OF_DAY, 0)
                selectedDate.set(Calendar.MINUTE, 0)
                selectedDate.set(Calendar.SECOND, 0)
                selectedDate.set(Calendar.MILLISECOND, 0)
                presenter.setDayID(selectedDate.time.time)

                val dayTextFormat = TimeFormat.dateFormat.format(selectedDate.time.time)
                binding.dateEditText.setText(dayTextFormat)
            },
            now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun pickTimeByDialog(isTimeFrom: Boolean){
        val now = Calendar.getInstance()
        val timePicker = TimePickerDialog(
            this.requireContext(),
            { _, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                if (isTimeFrom) {
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