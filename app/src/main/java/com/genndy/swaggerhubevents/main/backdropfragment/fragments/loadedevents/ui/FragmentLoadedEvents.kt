package com.genndy.swaggerhubevents.main.backdropfragment.fragments.loadedevents.ui

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.genndy.swaggerhubevents.R
import com.genndy.swaggerhubevents.apidata.ApiGetService
import com.genndy.swaggerhubevents.apidata.pojo.EventPOJO
import com.genndy.swaggerhubevents.databinding.FragmentLoadedEventsBinding
import com.genndy.swaggerhubevents.main.MainActivity
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.adapter.EventsAdapter
import com.genndy.swaggerhubevents.main.backdropfragment.fragments.model.Event
import com.genndy.swaggerhubevents.main.fragments.loadingsettings.model.DownloadSettingModel
import com.genndy.swaggerhubevents.main.fragmentservices.FragmentType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FragmentLoadedEvents() : FragmentType() {
    val type = FRAGMENT_LOADED_EVENTS
    private var _binding: FragmentLoadedEventsBinding? = null
    private val binding get() = _binding!!
    private var mMessage : TextView? = null
    private var mEvents : RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadedEventsBinding.inflate(inflater, container, false)
//        updateEvents()
        mEvents = binding.fragmentLoadedEventsView
        mMessage = binding.fragmentLoadedEventsMessage
        return binding.root
    }

    fun updateSavedEvents(events : List<Event>){
        if(mEvents!!.adapter != null){
            if(events != null){
                (mEvents!!.adapter as EventsAdapter).updateSavedEvents(events)
            }
        }
    }

    fun updateEvents() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.allsportdb.com/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService: ApiGetService = retrofit.create(ApiGetService::class.java)
        val mDownloadingIndicator = binding.fragmentLoadedDownloadingIndicator
        mDownloadingIndicator.visibility = View.VISIBLE
        mMessage!!.visibility = View.GONE
        (mDownloadingIndicator.drawable as AnimatedVectorDrawable).start()
        val sharedPreferences = requireContext().getSharedPreferences("apiLoading", Context.MODE_PRIVATE)
        val sport = sharedPreferences.getInt(DownloadSettingModel.SETTING_SPORT, 0)
        val country = sharedPreferences.getInt(DownloadSettingModel.SETTING_COUNTRY, 0)
        val response: Call<ArrayList<EventPOJO>> = apiService.getEvents(sport, country)
        mEvents!!.visibility = View.GONE

        response.enqueue(object : Callback<ArrayList<EventPOJO>> {
            override fun onResponse(
                call: Call<ArrayList<EventPOJO>>,
                response: Response<ArrayList<EventPOJO>>
            ) {
                val pojoEvents: ArrayList<EventPOJO>? = response.body()
                val eventList: ArrayList<Event> = ArrayList()
                for (i in pojoEvents!!.indices) {
                    val pojoEvent = pojoEvents[i]
                    val event = Event()
                    event.id = pojoEvent.id
                    if (pojoEvent.name != null) {
                        event.name = pojoEvent.name!!
                    }
                    if (pojoEvent.emoji != null) {
                        event.emoji = pojoEvent.emoji!!
                    }
                    if (pojoEvent.location != null){
                        event.location = pojoEvent.location!![0].name!!
                    }
                    if (pojoEvent.sport != null){
                        event.sport = pojoEvent.sport!!
                    }
                    if (pojoEvent.date != null) {
                        event.date = pojoEvent.date!!
                    }

                    if (pojoEvent.dateFrom != null) {
                        event.dateStart = pojoEvent.dateFrom!!
                    }

                    if (pojoEvent.dateTo != null) {
                        event.dateEnd = pojoEvent.dateTo!!
                    }
                    eventList.add(event)
                }
                if (eventList.size != 0) {
                    mEvents!!.visibility = View.VISIBLE
                    mMessage!!.visibility = View.GONE
                    mDownloadingIndicator.visibility = View.GONE
                    mEvents!!.adapter = EventsAdapter(eventList)

                } else {
                    mMessage!!.visibility = View.VISIBLE
                    mDownloadingIndicator.visibility = View.GONE
                    mMessage!!.text = "NO DATA"
                }
            }

            override fun onFailure(call: Call<ArrayList<EventPOJO>>, t: Throwable) {
                t.fillInStackTrace()
            }
        })
    }



    override fun getFragmentType(): Int {
        return FRAGMENT_LOADED_EVENTS
    }
}