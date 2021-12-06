package com.genndy.swaggerhubevents.main.calendar

import android.view.View
import android.widget.TextView
import com.genndy.swaggerhubevents.R
import com.genndy.swaggerhubevents.databinding.CalendarDayLayoutBinding
import com.kizitonwose.calendarview.ui.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
}