package com.example.calendarsymbersoft.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendarsymbersoft.R
import com.example.calendarsymbersoft.model.Event
import io.realm.RealmResults

class EventsAdapter(private val eventsList: List<Event>) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.eventTitle)
        val time: TextView = view.findViewById(R.id.eventTime)
        val description: TextView = view.findViewById(R.id.eventDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder{
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_rv_layout, parent, false)
        return EventViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val item = eventsList[position]
        holder.title.text = item.title
        holder.time.text = item.getTimeRange()
        holder.description.text = item.description
    }

    override fun getItemCount() = eventsList.size

}