package com.example.calendarsymbersoft.view.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
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
    private var _eventID: Int? = null
    private val eventID get() = _eventID!!
    private var dayId: Long? = null
    private var timeFrom: Long? = null
    private var timeTo: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _eventID = it.getInt("eventID")
            dayId = it.getLong("dayID")
            timeFrom = it.getLong("timeFrom")
            timeTo = it.getLong("timeTo")
        }
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.title.setText(presenter.getTitleByID(eventID))
        binding.dateEditText.setText(TimeFormat.dateFormat.format(presenter.getDayIdByID(eventID)))
        binding.timeFromEditText.setText(TimeFormat.timeFormat.format(presenter.getTimeFromByID(eventID)))
        binding.timeToEditText.setText(TimeFormat.timeFormat.format(presenter.getTimeToByID(eventID)))
        binding.descriptionEditText.setText(presenter.getDescriptionById(eventID))

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
            onUpdateBtnClicked()
        }

        binding.cancelButton.setOnClickListener {
            navToAnotherFragment(R.id.action_editEventFragment_to_calendarFragment)
        }
    }

    private fun onUpdateBtnClicked () {
        try {
            if (validFields()) {
                if (binding.descriptionEditText.text.isNullOrEmpty()) {
                    binding.descriptionEditText.setText(R.string.no_description)
                }
                val resultOfSaving = presenter.updateEvent(
                    id = eventID,
                    dayID = dayId!!,
                    timeFrom = timeFrom!!,
                    timeTo = timeTo!!,
                    description = binding.descriptionEditText.text.toString(),
                    title = binding.title.text.toString()
                )
                showMessage(resultOfSaving.toString())
                navToAnotherFragment(R.id.action_editEventFragment_to_calendarFragment)
            } else {
                showMessage(getString(R.string.incorrect_input))
            }
        } catch (e: Exception) {
            Toast.makeText(this.requireContext(), "Error: $e", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validFields(): Boolean {
        if (!(binding.title.text.toString().isEmpty() ||
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
                commitDialog.setPositiveButton(R.string.yes) { dialog, id ->
                    presenter.deleteEvent(id = eventID)
                    showMessage(getStringResource(R.string.event_deleted))
                    navToAnotherFragment(R.id.action_editEventFragment_to_calendarFragment)
                }
                commitDialog.setNegativeButton(R.string.no) { dialog, id -> }
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