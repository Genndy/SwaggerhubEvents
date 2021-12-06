package com.genndy.swaggerhubevents.main.fragments.calendar.calendarviewholder

import android.view.View
import android.widget.TextView
import com.genndy.swaggerhubevents.R
import com.kizitonwose.calendarview.ui.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = view.findViewById<TextView>(R.id.calendarDayText)

}