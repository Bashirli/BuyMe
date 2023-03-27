package com.bashirli.buyme.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bashirli.buyme.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val handler=Handler()
        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, ScreenActivity::class.java))
            finish()
        },1300)
    }
}