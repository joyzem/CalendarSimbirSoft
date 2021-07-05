package com.example.calendarsymbersoft.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarsymbersoft.R
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.databinding.FragmentCalendarBinding
import com.example.calendarsymbersoft.presenter.CalendarPresenter
import com.example.calendarsymbersoft.view.adapter.EventsAdapter
import java.util.*


class CalendarFragment : Fragment(),
    MainContract.View {

    private val presenter = CalendarPresenter()
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.eventsRV
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        val currentDay = Calendar.getInstance()
        setTimeToZero(currentDay)
        updateRecyclerViewDataset(currentDay.timeInMillis)

        binding.addEventBtn.setOnClickListener {
            navToAnotherFragment(R.id.action_calendarFragment_to_addEventFragment)
        }

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDay = Calendar.getInstance()
            selectedDay.set(Calendar.YEAR, year)
            selectedDay.set(Calendar.MONTH, month)
            selectedDay.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            setTimeToZero(selectedDay)
            updateRecyclerViewDataset(selectedDay.timeInMillis)
        }
    }

    private fun updateRecyclerViewDataset(day: Long) {
        val events = presenter.loadEventsBySelectedDate(day)
        recyclerView.adapter = EventsAdapter(this, eventsList = events)
        recyclerView.adapter!!.notifyDataSetChanged()
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


    private fun setTimeToZero(selectedDay: Calendar) {
        selectedDay.set(Calendar.HOUR_OF_DAY, 0)
        selectedDay.set(Calendar.MINUTE, 0)
        selectedDay.set(Calendar.SECOND, 0)
        selectedDay.set(Calendar.MILLISECOND, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}