package com.genndy.swaggerhubevents.main

import android.annotation.SuppressLint
import android.os.*
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.genndy.matches.backdrop.OnBottomSheetCallbacks
import com.genndy.swaggerhubevents.R
import com.genndy.swaggerhubevents.databinding.ActivityMainBinding
import com.genndy.swaggerhubevents.dbroom.models.EventViewModel
import com.genndy.swaggerhubevents.main.backdropfragment.WindowFragment
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.model.Event
import com.genndy.swaggerhubevents.main.fragments.loadingsettings.ui.FragmentLoadingSettings
import com.genndy.swaggerhubevents.main.fragmentservices.BackViewPagerAdapter
import com.genndy.swaggerhubevents.main.fragmentservices.FragmentType
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var listener: OnBottomSheetCallbacks? = null
    private var mBottomSheetBehavior: BottomSheetBehavior<View?>? = null
    private lateinit var windowFragment: WindowFragment
    private var savedEvents : ArrayList<Event>? = null
    lateinit var leaguesView : RecyclerView
    private var eventViewModel : EventViewModel? = null
    private var fragments : ArrayList<FragmentType>? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.elevation = 0f
        windowFragment = (supportFragmentManager.findFragmentById(R.id.filter_fragment) as WindowFragment?)!!
        fragments = ArrayList()
        fragments!!.add(FragmentLoadingSettings(this))
        binding.backViewPager.setOffscreenPageLimit(2);
        binding.backViewPager.adapter = BackViewPagerAdapter(fragments!!)
        connectToDB()
        Handler(Looper.myLooper()!!).postDelayed(Runnable {
            updateEventsFromDB()
        },1000)

    }

    override fun onStart() {
        super.onStart()
        configureBackdrop()
    }

    fun setOnBottomSheetCallbacks(onBottomSheetCallbacks: OnBottomSheetCallbacks) {
        this.listener = onBottomSheetCallbacks
    }

    fun openBottomSheet() {
        mBottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
        windowFragment.animateSheetSelector(R.drawable.avd_down)
    }

    fun closeBottomSheet() {
        mBottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        windowFragment.animateSheetSelector(R.drawable.avd_up)
    }

    fun addEventIntoDB(event : Event){
        AsyncTask.execute{
            eventViewModel!!.addEvent(event)
            Thread.sleep(500)
            eventViewModel!!.getAllEvents()
        }
    }

    fun removeEventFromDB(event : Event){
        AsyncTask.execute{
            eventViewModel!!.removeEvent(event)
            Thread.sleep(500)
            eventViewModel!!.getAllEvents()
        }
    }

    fun updateEventsFromDB(){
        AsyncTask.execute{

            eventViewModel!!.getAllEvents()
        }
    }

    fun updateEventsOnUI(events : ArrayList<Event>){
        windowFragment.updateSavedEvents(events)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            (fragments!![1] as FragmentCalendar).updateEvents(events)
        }
    }

    fun connectToDB(){
        if(eventViewModel == null){
            AsyncTask.execute {
                eventViewModel = EventViewModel( this@MainActivity, application)
            }

        }
    }

    private fun configureBackdrop() {
        (windowFragment.view?.parent as View).let { view ->
            BottomSheetBehavior.from(view).let { bs ->
                bs.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, slideOffset: Float) {}
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        // Call the interface to notify a state change
                        listener?.onStateChanged(bottomSheet, newState)
                    }
                })
                // Set the bottom sheet expanded by default
                bs.state = BottomSheetBehavior.STATE_EXPANDED

                mBottomSheetBehavior = bs
            }
        }
    }
}