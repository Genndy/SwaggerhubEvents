package com.genndy.swaggerhubevents.main.fragmentservices

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.savedevents.FragmentSavedEvents
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.loadedevents.ui.FragmentLoadedEvents
import com.genndy.swaggerhubevents.main.fragments.calendar.FragmentCalendar
import com.genndy.swaggerhubevents.main.fragments.loadingsettings.ui.FragmentLoadingSettings

class FragmentAdapter(fragments : ArrayList<FragmentType>, fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle ) {
    val fragments = fragments
    val fragmentManager = fragmentManager

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        var fr : Fragment? = null
        when(fragments[position].getFragmentType()){
            FragmentType.FRAGMENT_CALENDAR -> { fr = FragmentCalendar() }
            FragmentType.FRAGMENT_SAVED_EVENTS -> { fr = FragmentSavedEvents() }
            else -> { fr = FragmentLoadedEvents() }
        }
        return fr
    }
}