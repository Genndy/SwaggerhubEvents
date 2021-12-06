package com.genndy.swaggerhubevents.dbroom.models

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.genndy.swaggerhubevents.dbroom.database.AppDataBase
import com.genndy.swaggerhubevents.dbroom.repository.EventRepository
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.genndy.swaggerhubevents.main.MainActivity

class EventViewModel(_context : Context, application: Application) : AndroidViewModel(application) {
    private var readAllData: List<Event>
    private val repository: EventRepository
    private val dataBase: AppDataBase
    private val _application = application
    val context = _context

    init { val eventDAO = AppDataBase.getDatabase(context)!!.eventDAO()
        dataBase = AppDataBase.getDatabase(_application)!!
        repository = EventRepository(eventDAO!!)
        readAllData = repository.readAllData
        viewModelScope.launch (Dispatchers.IO){
            val allEvents : List<Event> = dataBase.eventDAO()!!.getAllEvents()
            readAllData = allEvents
        }
    }

    fun addEvent(event: Event){
        viewModelScope.launch (Dispatchers.IO){
            repository.addEvent(event)
        }
//        (context as MainActivity).updateEventsStatementFromDB()
    }

    fun removeEvent(event: Event){
        viewModelScope.launch (Dispatchers.IO){
            repository.removeEvent(event)
        }
//        (context as MainActivity).updateEventsStatementFromDB()
    }

    fun getAllEvents(){
        viewModelScope.launch (Dispatchers.IO) {
            val events = repository.getAllData() as ArrayList<Event>
            (context as MainActivity).updateEventsOnUI(events)

        }
    }
}