package com.genndy.swaggerhubevents.dbroom.repository

import com.genndy.swaggerhubevents.dbroom.dao.EventDAO
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.model.Event

class EventRepository (private val eventDAO: EventDAO) {
    val readAllData: List<Event> = eventDAO.getAllEvents()

    suspend fun addEvent(event : Event){
        eventDAO.addEvent(event)
    }

    fun removeEvent(event: Event){
        eventDAO.delete(event)
    }

    fun getAllData() : List<Event>{
        return  eventDAO.getAllEvents()
    }
}