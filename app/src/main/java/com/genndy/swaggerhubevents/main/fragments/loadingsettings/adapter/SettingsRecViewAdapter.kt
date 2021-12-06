package com.genndy.swaggerhubevents.main.fragments.loadingsettings.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.genndy.swaggerhubevents.R
import com.genndy.swaggerhubevents.main.fragments.loadingsettings.model.DownloadSettingModel

class SettingsRecViewAdapter(settings : ArrayList<DownloadSettingModel>) :
    RecyclerView.Adapter<SettingsRecViewAdapter.SettingsHolder>() {
    private var sharedPreferences : SharedPreferences? = null
    var context : Context? = null
    private var settings : ArrayList<DownloadSettingModel> = settings

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_setting, parent, false)
        return SettingsHolder(view)
    }

    @SuppressLint("CommitPrefEdits")
    override fun onBindViewHolder(holder: SettingsHolder, position: Int) {
        val mBody = holder.itemView.findViewById<ConstraintLayout>(R.id.item_setting_body)
        val mValue = holder.itemView.findViewById<TextView>(R.id.item_setting_value)
        val mSelector = holder.itemView.findViewById<ImageView>(R.id.item_setting_selector)

        val setting : DownloadSettingModel = settings[position]
        if(sharedPreferences == null){
            sharedPreferences = context!!.getSharedPreferences("apiLoading", Context.MODE_PRIVATE)
        }

        mValue.text = (setting.name + setting.emoji)
        if(setting.id != sharedPreferences!!.getInt(setting.parametr, 0)){
            mSelector.visibility = View.GONE
        }else{
            mSelector.visibility = View.VISIBLE
        }
        mBody.setOnClickListener {
            val editor = sharedPreferences!!.edit()
            editor.putInt(setting.parametr, setting.id)
            editor.apply()
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return settings.size
    }

    class SettingsHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}