package com.genndy.swaggerhubevents.main.backdropfragment

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.genndy.matches.backdrop.OnBottomSheetCallbacks
import com.genndy.swaggerhubevents.R
import com.genndy.swaggerhubevents.databinding.FragmentWindowBinding
import com.genndy.swaggerhubevents.main.MainActivity
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.loadedevents.ui.FragmentLoadedEvents
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.savedevents.FragmentSavedEvents
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.model.Event
import com.genndy.swaggerhubevents.main.fragmentservices.FragmentAdapter
import com.genndy.swaggerhubevents.main.fragmentservices.FragmentType
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class WindowFragment : BottomSheetDialogFragment(), OnBottomSheetCallbacks {
    private var _binding: FragmentWindowBinding? = null
    // This property is only valid between `onCreateView` and `onDestroyView`
    private val binding get() = _binding!!
    private var currentState: Int = BottomSheetBehavior.STATE_EXPANDED
    private var fragmentLoadedEvents : FragmentLoadedEvents? = null
    private var fragmentSavedEvents : FragmentSavedEvents? = null
    private var sharedPreferences : SharedPreferences? = null

    private var leagueName : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).setOnBottomSheetCallbacks(this)
        _binding = FragmentWindowBinding.inflate(inflater, container, false)
        val fragments : ArrayList<FragmentType> = ArrayList()
        fragments.add(FragmentLoadedEvents())
        fragments.add(FragmentSavedEvents())

        binding.frontViewPager.adapter = FragmentAdapter(fragments, childFragmentManager, lifecycle)

        binding.frontViewPager.setOffscreenPageLimit(2);
        TabLayoutMediator(binding.tablayout, binding.frontViewPager) {
            tab, position -> // Styling each tab here
            when (position) {
                0 -> {tab.text = "Loaded\nevents"}
                1 -> {tab.text = "Saved\nevents"}
            }
        }.attach()

        binding.updateButton.setOnClickListener {
            binding.updateButton.setImageResource(R.drawable.avd_update_data)
            (binding.updateButton.drawable as AnimatedVectorDrawable).start()
            (childFragmentManager.fragments[0] as FragmentLoadedEvents).updateEvents()
        }

        sharedPreferences = requireContext().getSharedPreferences("application", Context.MODE_PRIVATE)
        if(sharedPreferences!!.getBoolean("first_run", true)){
            sharedPreferences!!.edit().putBoolean("first_run", false)
            sharedPreferences!!.edit().commit()
            Handler(Looper.myLooper()!!).postDelayed({
                (childFragmentManager.fragments[0] as FragmentLoadedEvents).updateEvents()
            }, 3000)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.filterImage.setOnClickListener {
            if (currentState == BottomSheetBehavior.STATE_EXPANDED) {
                (activity as MainActivity).closeBottomSheet()
            } else {
                (activity as MainActivity).openBottomSheet()
            }
        }
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        currentState = newState
        when (newState) {
            BottomSheetBehavior.STATE_EXPANDED -> {
                binding.filterImage.setImageResource(R.drawable.vd_down)
            }
            BottomSheetBehavior.STATE_COLLAPSED -> {
                binding.filterImage.setImageResource(R.drawable.vd_up)
            }
        }
    }

    fun updateSavedEvents(events: List<Event>) {
        (context as MainActivity).runOnUiThread {
            binding.updateButton.setImageResource(R.drawable.avd_update_data)
            (binding.updateButton.drawable as AnimatedVectorDrawable).start()
        }

        if(fragmentLoadedEvents == null){
            fragmentLoadedEvents = childFragmentManager.fragments[0] as FragmentLoadedEvents
        }
        if(fragmentLoadedEvents != null){
            fragmentLoadedEvents!!.updateSavedEvents(events)
        }
        if(fragmentSavedEvents == null){
            fragmentSavedEvents = childFragmentManager.fragments[1] as FragmentSavedEvents
        }
        if(fragmentSavedEvents != null){
            fragmentSavedEvents!!.updateSavedEvents(events)
        }
    }

    fun animateSheetSelector(avdUp: Int) {
        binding.filterImage.setImageResource(avdUp)
        (binding.filterImage.drawable as AnimatedVectorDrawable).start()
    }
}
