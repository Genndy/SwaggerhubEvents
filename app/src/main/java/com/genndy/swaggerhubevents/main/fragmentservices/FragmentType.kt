package com.genndy.swaggerhubevents.main.fragmentservices

import androidx.fragment.app.Fragment

abstract class FragmentType : Fragment() {
    companion object {
        const val FRAGMENT_SETTINGS = 0
        const val FRAGMENT_CALENDAR = 1
        const val FRAGMENT_LOADED_EVENTS = 2
        const val FRAGMENT_SAVED_EVENTS = 3
    }
    abstract fun getFragmentType() : Int
}