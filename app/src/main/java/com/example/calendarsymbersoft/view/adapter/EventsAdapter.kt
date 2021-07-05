package com.example.calendarsymbersoft.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarsymbersoft.R
import com.example.calendarsymbersoft.contract.MainContract
import com.example.calendarsymbersoft.model.Event
import java.lang.Exception

class EventsAdapter(
    private val calView: MainContract.View,
    private val eventsList: List<Event> = listOf())
    : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    class EventViewHolder(view: View, calView: MainContract.View,  eventsList: List<Event>) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.findViewById(R.id.eventTitle)
        val time: TextView = view.findViewById(R.id.eventTime)
        val description: TextView = view.findViewById(R.id.eventDescription)

        // On card listener
        init {
            view.setOnClickListener {
                try {
                    val positionIndex = absoluteAdapterPosition
                    val bundle = Bundle()
                    bundle.putInt("eventID", eventsList[positionIndex].id!!)
                    calView.navToAnotherFragment(
                        R.id.action_calendarFragment_to_editEventFragment,
                        bundle
                    )
                } catch (e: Exception) {
                    calView.showMessage(e.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder{
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_rv_layout, parent, false)
        return EventViewHolder(adapterLayout, calView, eventsList)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val item = eventsList[position]
        holder.title.text = item.title
        holder.time.text = item.getTimeRange()
        holder.description.text = item.description
    }

    override fun getItemCount() = eventsList.size

}