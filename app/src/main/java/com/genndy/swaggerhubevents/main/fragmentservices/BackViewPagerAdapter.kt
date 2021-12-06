package com.genndy.swaggerhubevents.main.fragmentservices

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.savedevents.FragmentSavedEvents
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.loadedevents.ui.FragmentLoadedEvents
import com.genndy.swaggerhubevents.main.fragments.calendar.FragmentCalendar
import com.genndy.swaggerhubevents.main.fragments.loadingsettings.ui.FragmentLoadingSettings

class BackViewPagerAdapter : RecyclerView.Adapter<BackViewPagerAdapter.PagerVH>{
    private var fragments : ArrayList<FragmentType> = ArrayList()
    private var _fragments : ArrayList<PagerVH> = ArrayList()

    private var fragmentLoadedEvents : FragmentLoadedEvents? = null

    constructor(fragments : ArrayList<FragmentType>){
        this.fragments = fragments
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        lateinit var view : View
        when (viewType){
            FragmentType.FRAGMENT_SETTINGS -> view = FragmentLoadingSettings(parent.context).onCreateView(
                LayoutInflater.from(parent.context), parent, null)

            FragmentType.FRAGMENT_CALENDAR -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                view = FragmentCalendar().onCreateView(
                    LayoutInflater.from(parent.context), parent, null)
            }

            FragmentType.FRAGMENT_LOADED_EVENTS -> {

                val fragment = FragmentLoadedEvents().onCreateView(
                    LayoutInflater.from(parent.context), parent, null)

                view = fragment
            }

            FragmentType.FRAGMENT_SAVED_EVENTS -> view = FragmentSavedEvents().onCreateView(
                LayoutInflater.from(parent.context), parent, null)
        }
        val v = PagerVH(view)
        _fragments.add(v)
        return v
    }

    fun getItem(i : Int) : PagerVH {
        return _fragments[i]
    }

    override fun getItemViewType(position: Int): Int {
        val i = fragments[position].getFragmentType()
        return i
    }

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
    class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)
}