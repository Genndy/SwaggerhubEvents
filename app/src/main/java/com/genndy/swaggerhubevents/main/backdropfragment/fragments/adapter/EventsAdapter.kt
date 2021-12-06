package com.genndy.swaggerhubevents.main.backdropfragment.fragments.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.genndy.swaggerhubevents.R
import com.genndy.swaggerhubevents.main.MainActivity
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.model.Event
import java.time.*
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class EventsAdapter(events : ArrayList<Event>) : RecyclerView.Adapter<EventsAdapter.EventsHolder>() {
    var events : ArrayList<Event> = events
    var savedEvents : ArrayList<Event>? = null
    var context : Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsHolder {
        if(context == null){
            context = parent.context
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventsHolder(view)
    }

    override fun onBindViewHolder(holder: EventsHolder, position: Int) {
        val mName = holder.itemView.findViewById<TextView>(R.id.item_event_name)
        val mSport = holder.itemView.findViewById<TextView>(R.id.item_event_sport)
        val mLocation = holder.itemView.findViewById<TextView>(R.id.item_event_location)
        val mDate = holder.itemView.findViewById<TextView>(R.id.item_event_date)
        val mDateStart = holder.itemView.findViewById<TextView>(R.id.item_event_date_start)
        val mDateEnd = holder.itemView.findViewById<TextView>(R.id.item_event_date_end)
        val mSavedStatement = holder.itemView.findViewById<ImageButton>(R.id.item_event_saved_statement)
        val mProgressBar = holder.itemView.findViewById<ProgressBar>(R.id.item_event_progress_bar)
        val event = events[position]

        mName.text = event.name
        mSport.text = event.sport
        mLocation.text = event.location
        mDate.text = event.date
        mDateStart.text = event.dateStart.substring(0, 10)
        mDateEnd.text = event.dateEnd.substring(0, 10)
        event.isSaved = checkForSaving(event)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss")
            val dateStartMilliseconds = LocalDate.parse(event.dateStart, dateFormat)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli()

            val dateEndMilliseconds = LocalDate.parse(event.dateEnd, dateFormat)
                .atStartOfDay(ZoneOffset.UTC)
                .toInstant()
                .toEpochMilli()

            if(System.currentTimeMillis() in (dateStartMilliseconds + 1)
                until dateEndMilliseconds){
                mProgressBar.max = dateEndMilliseconds.toInt()
                mProgressBar.min = dateStartMilliseconds.toInt()
                mProgressBar.progress = System.currentTimeMillis().toInt()
            }else{
                mProgressBar.visibility = View.GONE
            }
        }else{
            mProgressBar.visibility = View.GONE
        }

        if (event.isSaved){
            mSavedStatement.setImageResource(R.drawable.ic_saved)
        }else{
            mSavedStatement.setImageResource(R.drawable.ic_add)
        }

        mSavedStatement.setOnClickListener {
            if(!event.isSaved){
                (context as MainActivity).addEventIntoDB(event)
            }else{
                (context as MainActivity).removeEventFromDB(event)
            }
            notifyDataSetChanged()
        }
    }

    fun checkForSaving(event : Event) : Boolean{
        if(savedEvents == null){
            return false
        }
        for(savedEvent in savedEvents!!){
            if(event.id == savedEvent.id){
                return true
            }
        }
        return false
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun updateSavedEvents(events: List<Event>) {
        savedEvents = (events as ArrayList<Event>)
        if(context != null){
            (context as MainActivity).runOnUiThread {
                notifyDataSetChanged()
            }
        }
    }

    class EventsHolder(itemView: View) : RecyclerView.ViewHolder(itemView){}
}