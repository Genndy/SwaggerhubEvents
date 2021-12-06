package com.genndy.swaggerhubevents.main.fragments.loadingsettings.ui

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.genndy.swaggerhubevents.R
import com.genndy.swaggerhubevents.apidata.ApiGetService
import com.genndy.swaggerhubevents.apidata.pojo.CountryPOJO
import com.genndy.swaggerhubevents.apidata.pojo.SportPOJO
import com.genndy.swaggerhubevents.databinding.FragmentSettingsBinding
import com.genndy.swaggerhubevents.main.fragments.loadingsettings.adapter.SettingsRecViewAdapter
import com.genndy.swaggerhubevents.main.fragments.loadingsettings.model.DownloadSettingModel
import com.genndy.swaggerhubevents.main.fragmentservices.FragmentType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FragmentLoadingSettings(_context : Context) : FragmentType() {
    val type = FRAGMENT_SETTINGS
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val thisContext = _context

    private var mSportSettingText : TextView? = null
    private var mSportSettingHider : ImageView? = null
    private var mSportSettingHeader : LinearLayout? = null
    private var mSportsView : RecyclerView? = null

    private var mCountriesSettingText : TextView? = null
    private var mCountrySettingHider : ImageView? = null
    private var mCountrySettingHeader : LinearLayout? = null
    private var mCountriesView : RecyclerView? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val mPrivacyPolicy = binding.privacyPolicy
        mSportsView = binding.fragmentSettingSportsView
        mSportSettingHider = binding.fragmentSettingSportHider
        mSportSettingText = binding.fragmentSettingSportText
        mSportSettingHeader = binding.fragmentSettingSportHeader

        mCountriesView = binding.fragmentSettingCountriesView
        mCountrySettingHeader = binding.fragmentSettingCountriesHeader
        mCountriesSettingText = binding.fragmentSettingCountriesText
        mCountrySettingHider = binding.fragmentSettingCountryHider

        mSportSettingHeader!!.setOnClickListener {
            mSettingHeaderClick(DownloadSettingModel.SETTING_SPORT, mSportsView!!, mSportSettingHider!!)
        }

        mCountrySettingHeader!!.setOnClickListener {
            mSettingHeaderClick(DownloadSettingModel.SETTING_COUNTRY, mCountriesView!!, mCountrySettingHider!!)
        }

        mPrivacyPolicy.setOnClickListener{
            CustomTabsIntent.Builder().build().launchUrl(thisContext, Uri.parse("https://google.com"))
        }
        return binding.root
    }

    fun mSettingHeaderClick(setting : String, view : RecyclerView, hider : ImageView) {
        if(view.visibility == View.VISIBLE){
            if (view.adapter != null){
                hider.setImageResource(R.drawable.avd_down)
                view.visibility = View.GONE
            }else {
                hider.setImageResource(R.drawable.avd_update_data)
                updateSettingVars(setting, view, hider)
            }
        }else{
            if(view.adapter != null){
                hider.setImageResource(R.drawable.avd_down)
                view.visibility = View.VISIBLE
            }else{
                hider.setImageResource(R.drawable.avd_update_data)
                updateSettingVars(setting, view, hider)
            }
        }
        (hider.drawable as AnimatedVectorDrawable).start()
    }

    fun updateSettingVars(parameter : String, view : RecyclerView, hider : ImageView){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.allsportdb.com/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService: ApiGetService = retrofit.create(ApiGetService::class.java)

        when(parameter){
            DownloadSettingModel.SETTING_SPORT -> {
                val response: Call<ArrayList<SportPOJO>> = apiService.getSports()
                response.enqueue(object : Callback<ArrayList<SportPOJO>> {
                    override fun onResponse(call: Call<ArrayList<SportPOJO>>, response: Response<ArrayList<SportPOJO>>) {
                        val pojoSportPOJOList : ArrayList<SportPOJO>? = response.body()
                        val sportList : ArrayList<DownloadSettingModel> = ArrayList()
                        for (i in pojoSportPOJOList!!.indices) {
                            val pojoSportPOJO = pojoSportPOJOList[i]
                            val setting = DownloadSettingModel()
                            setting.id = pojoSportPOJO.id
                            if(pojoSportPOJO.name != null){
                                setting.name = pojoSportPOJO.name!!
                            }
                            if(pojoSportPOJO.emoji != null){
                                setting.emoji = pojoSportPOJO.emoji!!
                            }
                            setting.parametr = DownloadSettingModel.SETTING_SPORT
                            if (sportList.isEmpty()){
                                val nullSetting = DownloadSettingModel()
                                nullSetting.name = "Not selected"
                                nullSetting.parametr = DownloadSettingModel.SETTING_SPORT
                                sportList.add(nullSetting)
                            }
                            sportList.add(setting)
                        }
                        if(sportList.size != 0){
                            hider.setImageResource(R.drawable.avd_down)
                            (hider.drawable as AnimatedVectorDrawable).start()
                            view.visibility = View.VISIBLE
                            view.adapter = SettingsRecViewAdapter(sportList)
                        }else{
                            updateSettingVars(DownloadSettingModel.SETTING_SPORT, view, hider)
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<SportPOJO>>, t: Throwable) {
                        t.fillInStackTrace()
                        updateSettingVars(DownloadSettingModel.SETTING_SPORT, view, hider)
                    }
                })
            }

            DownloadSettingModel.SETTING_COUNTRY -> {
                val response: Call<ArrayList<CountryPOJO>> = apiService.getCountries()
                response.enqueue(object : Callback<ArrayList<CountryPOJO>> {
                    override fun onResponse(call: Call<ArrayList<CountryPOJO>>, response: Response<ArrayList<CountryPOJO>>) {
                        val countriesPojo : ArrayList<CountryPOJO>? = response.body()
                        val countriesList : ArrayList<DownloadSettingModel> = ArrayList()
                        for (i in countriesPojo!!.indices) {
                            val countryPojo = countriesPojo[i]
                            val setting = DownloadSettingModel()
                            setting.id = countryPojo.id
                            if(countryPojo.name != null){
                                setting.name = countryPojo.name!!
                            }
                            if(countryPojo.emoji != null){
                                setting.emoji = countryPojo.emoji!!
                            }
                            setting.parametr = DownloadSettingModel.SETTING_COUNTRY
                            if (countriesList.isEmpty()){
                                val nullSetting = DownloadSettingModel()
                                nullSetting.name = "Not selected"
                                nullSetting.parametr = DownloadSettingModel.SETTING_COUNTRY
                                countriesList.add(nullSetting)
                            }
                            countriesList.add(setting)
                        }
                        if(countriesList.size != 0){
                            view.visibility = View.VISIBLE
                            view.adapter = SettingsRecViewAdapter(countriesList)
                        }else{
                            updateSettingVars(DownloadSettingModel.SETTING_COUNTRY, view, hider)
                        }
                    }
                    override fun onFailure(call: Call<ArrayList<CountryPOJO>>, t: Throwable) {
                        t.fillInStackTrace()
                        updateSettingVars(DownloadSettingModel.SETTING_COUNTRY, view, hider)
                    }
                })
            }
        }
    }

    override fun getFragmentType(): Int {
        return FRAGMENT_SETTINGS
    }
}