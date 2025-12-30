package com.example.travenor.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.travenor.R

class CalendarFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        view.findViewById<TextView>(R.id.tvFragmentName).text = "Calendar Fragment"
        view.setBackgroundColor(resources.getColor(android.R.color.holo_red_dark, null))
        return view
    }
}