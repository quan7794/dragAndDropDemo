package com.raywenderlich.android.animationDemo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.raywenderlich.android.remoteDemo.R
import com.raywenderlich.android.remoteDemo.databinding.FragmentAnimationBinding

class AnimationFragment: Fragment() {
    lateinit var binding: FragmentAnimationBinding
    private val zoomInAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext().applicationContext, R.anim.zoom_in)
    }
    private val shakeAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext().applicationContext , R.anim.shake_anim)
    }
    private val moveAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext().applicationContext , R.anim.move_anim)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAnimationBinding.inflate(inflater, container, false)
        println("Start anim fragment")
        binding.setupViewAction()
        return binding.root
    }

    private fun FragmentAnimationBinding.setupViewAction() {
        shakeItem.setOnClickListener {
            Log.d("shakeItemClick", "entry")
            shakeItem.startAnimation(shakeAnim)
        }
        zoomInItem.setOnClickListener {
            Log.d("zoomInClick", "entry")
            zoomInItem.startAnimation(shakeAnim)
        }
        zoomOutItem.setOnClickListener {
            Log.d("zoomOutItemClick", "entry")
            zoomOutItem.startAnimation(moveAnim)
        }
    }
}


