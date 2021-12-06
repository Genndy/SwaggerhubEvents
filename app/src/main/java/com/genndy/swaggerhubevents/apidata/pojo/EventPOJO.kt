package com.genndy.swaggerhubevents.apidata.pojo

import java.util.*

class EventPOJO {
    var id = 0
    var name: String? = null
    var date: String? = null
    var dateFrom: String? = null
    var dateTo: String? = null
    var sportId = 0
    var sport: String? = null
    var emoji: String? = null
    var competitionId = 0
    var competition: String? = null
    var continentId = 0
    var continent: String? = null
    var url: String? = null
    var webUrl: String? = null
    var wikiUrl: String? = null
    var ticketsUrl: String? = null
    var facebookUrl: String? = null
    var twitterUrl: String? = null
    var liveUrl: String? = null
    var liveUrlPaid = false
    var logoUrl: String? = null
    var logoThumbnailUrl: String? = null
    var logoSmallUrl: String? = null
    var dateModified: String? = null
    var location: List<Location>? = null

    class Location2 {
        var id = 0
        var name: String? = null
        var lat = 0.0
        var lng = 0.0
    }

    class Location {
        var id = 0
        var regionId: Any? = null
        var name: String? = null
        var continent: String? = null
        var continentId = 0
        var code: String? = null
        var emoji: String? = null
        var flagUrl: String? = null
        var locations: List<Location2>? = null
    }
}