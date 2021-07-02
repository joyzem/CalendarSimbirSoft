package com.example.calendarsymbersoft.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.databinding.FragmentAddEventBinding

class AddEventFragment : Fragment(), MainContract.AddEventView {

    private var _binding: FragmentAddEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEventBinding.inflate(inflater, container, false)
        return binding.root
    }

}