package com.ngangavic.test.fancyprogress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bitvale.lightprogress.LightProgress
import com.ngangavic.test.R

class LightProgressActivity : AppCompatActivity() {
    lateinit var light:LightProgress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_progress)
        light=findViewById(R.id.light)
        light.on()
    }
}