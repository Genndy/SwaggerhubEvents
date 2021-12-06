package com.genndy.swaggerhubevents.apidata

import com.genndy.swaggerhubevents.apidata.pojo.CountryPOJO
import com.genndy.swaggerhubevents.apidata.pojo.EventPOJO
import com.genndy.swaggerhubevents.apidata.pojo.SportPOJO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiGetService {
    @Headers("Authorization: Bearer 570ca733-0f0c-47c8-a6da-b318e6f22c80")
    @GET("sports")
    fun getSports() : Call<ArrayList<SportPOJO>>

    @Headers("Authorization: Bearer 570ca733-0f0c-47c8-a6da-b318e6f22c80")
    @GET("countries")
    fun getCountries(): Call<ArrayList<CountryPOJO>>

    @Headers("Authorization: Bearer 570ca733-0f0c-47c8-a6da-b318e6f22c80")
    @GET("calendar")
    fun getEvents(
        @Query("sportId") sportId : Int,
        @Query("countryId") countryId : Int,
        @Query("dateFrom") dateFrom : String = "2010-01-01",
        @Query("dateTo") dateTo : String = "2030-01-01"
    ): Call<ArrayList<EventPOJO>>
}