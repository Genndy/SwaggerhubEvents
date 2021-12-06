package com.genndy.swaggerhubevents.dbroom.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.model.Event

@Dao
interface EventDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEvent(vararg event: Event?)

    @Delete
    fun delete(vararg event: Event?)

    @Query("SELECT * FROM events_table")
    fun getAllEvents() : List<Event>
}