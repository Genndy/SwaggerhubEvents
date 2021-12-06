package com.genndy.swaggerhubevents.main.backdropfragment.fragments.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*
@Entity(tableName = "events_table")
class Event {
    @PrimaryKey
    var id = 0
    var name = ""
    var emoji = ""
    var location = ""
    var sport = ""
    var date = ""
    var dateStart = ""
    var dateEnd = ""
    var description = ""
    var progress = ""
    var eventBegins = ""
    var eventEnds = ""
    @Ignore
    var isSaved = false
}