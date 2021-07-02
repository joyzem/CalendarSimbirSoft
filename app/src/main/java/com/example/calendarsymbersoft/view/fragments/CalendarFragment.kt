package com.example.calendarsymbersoft.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.databinding.FragmentCalendarBinding
import com.example.calendarsymbersoft.presenter.CalendarPresenter


class CalendarFragment : Fragment(), MainContract.CalendarView {

    private val presenter = CalendarPresenter(this)
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.addEventBtn.setOnClickListener {
            presenter.addEventBtnWasClicked(this.requireView())
        }


    }

}