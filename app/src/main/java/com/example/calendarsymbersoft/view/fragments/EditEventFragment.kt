package com.example.calendarsymbersoft.view.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.calendarsymbersoft.R
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.databinding.FragmentEditEventBinding
import com.example.calendarsymbersoft.model.TimeFormat
import com.example.calendarsymbersoft.presenter.EditEventPresenter
import java.lang.Exception
import java.util.*

class EditEventFragment : Fragment(), MainContract.View {

    private var _binding: FragmentEditEventBinding? = null
    private val binding get() = _binding!!
    private val presenter = EditEventPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            presenter.setEventID(it.getInt("eventID"))
            presenter.fillAllByID()
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditEventBinding.inflate(inflater, container, false)
        binding.title.setText(presenter.getEventTitle())
        binding.dateEditText.setText(TimeFormat.dateFormat.format(presenter.getEventDayId()))
        binding.timeFromEditText.setText(TimeFormat.timeFormat.format(presenter.getEventTimeFrom()))
        binding.timeToEditText.setText(TimeFormat.timeFormat.format(presenter.getEventTimeTo()))
        binding.descriptionEditText.setText(presenter.getEventDescription())
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
            onUpdateBtnClicked()
        }

        binding.cancelButton.setOnClickListener {
            navToAnotherFragment(R.id.action_editEventFragment_to_calendarFragment)
        }
    }

    private fun onUpdateBtnClicked () {
        try {
            if (validFields()) {
                presenter.setEventTitle(binding.title.text.toString())
                if (binding.descriptionEditText.text.isNullOrEmpty()) {
                    presenter.setDescription(getString(R.string.no_description))
                } else {
                    presenter.setDescription(binding.descriptionEditText.text.toString())
                }
                presenter.updateEvent()
                showMessage(getString(R.string.event_updated))
                navToAnotherFragment(R.id.action_editEventFragment_to_calendarFragment)
            } else {
                showMessage(getString(R.string.incorrect_input))
            }
        } catch (e: Exception) {
            Toast.makeText(this.requireContext(), "Error: $e", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validFields(): Boolean {
        return binding.title.text.toString().isNotEmpty() &&
                presenter.getEventTimeFrom() < presenter.getEventTimeTo()
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
            this.requireContext(), { _, hourOfDay, minute ->
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_event_menu_layout, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_event_icon -> {
                val commitDialog = AlertDialog.Builder(this.requireContext())
                    .setTitle(R.string.delete_the_event)
                    .setMessage(R.string.you_sure)
                    .setIcon(R.drawable.delete_icon)
                commitDialog.setPositiveButton(R.string.yes) { _, _ ->
                    presenter.deleteEvent()
                    showMessage(getStringResource(R.string.event_deleted))
                    navToAnotherFragment(R.id.action_editEventFragment_to_calendarFragment)
                }
                commitDialog.setNegativeButton(R.string.no) { _, _ -> }
                commitDialog.show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getStringResource(resourceId: Int): String {
        return getString(resourceId)
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