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

    /**
     * Fragment for events editing
     * Navigated from CalendarFragment by clicking on the event
     */

    // ViewBinding setting
    private var _binding: FragmentEditEventBinding? = null
    private val binding get() = _binding!!
    // Presenter for the fragment
    private val presenter = EditEventPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // Getting of eventID from the bundle
            presenter.setEventID(it.getInt("eventID"))
            presenter.fillAllByID()
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Setting text into textEdits
        _binding = FragmentEditEventBinding.inflate(inflater, container, false)
        binding.title.setText(presenter.getEventTitle())
        binding.dateEditText.setText(TimeFormat.dateFormat.format(presenter.getEventDayId()))
        binding.timeFromEditText.setText(TimeFormat.timeFormat.format(presenter.getEventTimeFrom()))
        binding.timeToEditText.setText(TimeFormat.timeFormat.format(presenter.getEventTimeTo()))
        binding.descriptionEditText.setText(presenter.getEventDescription())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Listeners setting
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
            // Checking for valid user input
            if (presenter.validInput(binding.title.toString())) {
                // Setting event's title to presenter
                presenter.setEventTitle(binding.title.text.toString())
                if (binding.descriptionEditText.text.isNullOrEmpty()) {
                    // Setting description via strings' resources
                    presenter.setDescription(getString(R.string.no_description))
                } else {
                    // ... Or entered description
                    presenter.setDescription(binding.descriptionEditText.text.toString())
                }
                // Updating after checking
                presenter.updateEvent()
                // Navigating to the CalendarFragment
                navToAnotherFragment(R.id.action_editEventFragment_to_calendarFragment)
            } else {
                // Toast "Incorrect input"
                showMessage(getString(R.string.incorrect_input))
            }
        } catch (e: Exception) {
            showMessage("Error: $e")
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
     timeFrom textEdit or timeTo textEdit*/ ){
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Options menu inflating
        inflater.inflate(R.menu.edit_event_menu_layout, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // On menu item click
            R.id.delete_event_icon -> {
                // "Delete the event" dialog creating
                val commitDialog = AlertDialog.Builder(this.requireContext())
                    .setTitle(R.string.delete_the_event)
                    .setMessage(R.string.you_sure)
                    .setIcon(R.drawable.delete_icon)
                commitDialog.setPositiveButton(R.string.yes) { _, _ ->
                    // Delete the event
                    presenter.deleteEvent()
                    // Toast "Event was deleted"
                    showMessage(getStringResource(R.string.event_deleted))
                    // Navigates to the CalendarFragment
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