package com.raywenderlich.android.animationDemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.android.remoteDemo.R

class AnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        if (savedInstanceState == null) {
        println("Init fragment")
            supportFragmentManager.beginTransaction()
                .add(R.id.container, AnimationFragment(), AnimationFragment::class.java.simpleName)
                .commit()
        }

    }

}