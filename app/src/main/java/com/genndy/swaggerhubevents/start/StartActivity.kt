package com.genndy.swaggerhubevents.start

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.widget.ImageButton
import android.widget.ImageView
import androidx.browser.customtabs.CustomTabsIntent
import com.genndy.swaggerhubevents.R
import com.genndy.swaggerhubevents.main.MainActivity

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val mPreloaderLogo = findViewById<ImageView>(R.id.preloader_logo)
        val anim = AlphaAnimation(0f, 1f)

        anim.duration = 2500
        mPreloaderLogo.startAnimation(anim)

        Handler(Looper.myLooper()!!).postDelayed({
            val intent = Intent(this@StartActivity, MainActivity::class.java)
            startActivity(intent)
            onBackPressed()
        }, 3200)
    }
}
