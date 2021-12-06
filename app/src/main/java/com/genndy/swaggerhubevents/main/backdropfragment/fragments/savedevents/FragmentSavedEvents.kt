package com.genndy.swaggerhubevents.main.backdropfragment.fragments.savedevents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.genndy.swaggerhubevents.databinding.FragmentSavedEventsBinding
import com.genndy.swaggerhubevents.main.MainActivity
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.adapter.EventsAdapter
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.model.Event
import com.genndy.swaggerhubevents.main.fragmentservices.FragmentType

class FragmentSavedEvents : FragmentType() {
    private var _binding: FragmentSavedEventsBinding? = null
    private val binding get() = _binding!!
    private var mSavedEvents : RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedEventsBinding.inflate(inflater, container, false)
        mSavedEvents = binding.savedEventsView
        return binding.root
    }

    override fun getFragmentType(): Int {
        return FRAGMENT_SAVED_EVENTS
    }

    fun updateSavedEvents(events: List<Event>) {
        (context as MainActivity).runOnUiThread {
            mSavedEvents!!.adapter = EventsAdapter((events as ArrayList<Event>))
            (mSavedEvents!!.adapter as EventsAdapter).savedEvents = events
        }
    }
}