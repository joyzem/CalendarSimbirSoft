package com.example.calendarsymbersoft.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.navigation.NavDirections
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

    private val presenter = CalendarPresenter(this)
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView = binding.eventsRV
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        updateRecyclerViewDataset(CalendarView(this.requireContext()))

        binding.addEventBtn.setOnClickListener {
            presenter.addEventBtnWasClicked()
        }

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            updateRecyclerViewDataset(view)
        }
    }

    private fun updateRecyclerViewDataset(view: CalendarView) {
        val events = presenter.loadEventsBySelectedDate(view)
        recyclerView.adapter = EventsAdapter(events)
        recyclerView.adapter!!.notifyDataSetChanged()
    }

    override fun moveToAnotherFragment() {
        val action = CalendarFragmentDirections
            .actionCalendarFragmentToAddEventFragment()
        this.findNavController().navigate(action)
    }

    override fun getStringResource(resourceId: Int): String {
        return getString(resourceId)
    }

}