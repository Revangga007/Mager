package com.mager.gamer.ui.postingan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mager.gamer.databinding.ActivityBuatPostinganBinding

class BuatPostinganActivity : AppCompatActivity() {
    companion object {
        const val INTENT_IMAGE_MODE = "INTENT_IMAGE_MODE"
        const val INTENT_LIVE_MODE = "INTENT_LIVE_MODE"
    }
    private lateinit var binding : ActivityBuatPostinganBinding
    private var isImageMode = false
    private var isLiveMode = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuatPostinganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            val intentImage = it.getBoolean(INTENT_IMAGE_MODE, false)
            isImageMode = intentImage
            val intentLive = it.getBoolean(INTENT_LIVE_MODE, false)
            isLiveMode = intentLive
            showHideMode()
        }

        binding.cardPhoto.setOnClickListener {
            isImageMode = ! isImageMode
            isLiveMode = false
            showHideMode()
        }
        binding.cardLive.setOnClickListener {
            isLiveMode = ! isLiveMode
            isImageMode = false
            showHideMode()
        }

        binding.cardPost.setOnClickListener {
            isImageMode = false
            isLiveMode = false
            showHideMode()
        }
    }

    private fun showHideMode() {
        if (isImageMode) {
            binding.layoutImage.visibility = View.VISIBLE
        } else {
            binding.layoutImage.visibility = View.GONE
        }
        if (isLiveMode) {
            binding.layoutLive.visibility = View.VISIBLE
        } else {
            binding.layoutLive.visibility = View.GONE
        }
    }
}