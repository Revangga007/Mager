package com.mager.gamer.ui.postingan

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.bumptech.glide.Glide
import com.mager.gamer.databinding.ActivityBuatPostinganBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class BuatPostinganActivity : AppCompatActivity() {
    companion object {
        const val INTENT_IMAGE_MODE = "INTENT_IMAGE_MODE"
        const val INTENT_LIVE_MODE = "INTENT_LIVE_MODE"
    }

    private lateinit var binding: ActivityBuatPostinganBinding
    private val viewModel: DetailPostinganViewModel by viewModels()
    private var isImageMode = false
    private var isLiveMode = false
    private var selectedFiles = mutableListOf<File>()

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
            isImageMode = true
            isLiveMode = false
            showHideMode()
        }
        binding.cardLive.setOnClickListener {
            isLiveMode = true
            isImageMode = false
            showHideMode()
        }

        binding.cardPost.setOnClickListener {
            isImageMode = false
            isLiveMode = false
            showHideMode()
        }

        binding.layoutImage.setOnClickListener {
            requestAccessForFile()
        }
        binding.imgClose.setOnClickListener {
            showHideImagePreview(false)
            selectedFiles.clear()
            binding.imgPreview.setImageDrawable(null)
        }
    }

    private fun showHideImagePreview(show: Boolean) {
        if (show) {
            binding.layoutImage.visibility = View.GONE
            binding.imgPreview.visibility = View.VISIBLE
            binding.imgClose.visibility = View.VISIBLE

        } else {
            binding.layoutImage.visibility = View.VISIBLE
            binding.imgPreview.visibility = View.GONE
            binding.imgClose.visibility = View.GONE
        }
    }

    private fun showHideMode() {
        showHideImagePreview(false)
        selectedFiles.clear()
        binding.imgPreview.setImageDrawable(null)
        when {
            isImageMode -> {
                binding.layoutImage.visibility = View.VISIBLE
                binding.layoutLive.visibility = View.GONE
                binding.txtLink.visibility = View.GONE
            }
            isLiveMode -> {
                binding.layoutImage.visibility = View.GONE
                binding.layoutLive.visibility = View.VISIBLE
                binding.txtLink.visibility = View.VISIBLE
            }
            else -> {
                binding.layoutImage.visibility = View.GONE
                binding.txtLink.visibility = View.GONE
                binding.layoutLive.visibility = View.GONE
            }
        }
    }

    private fun requestAccessForFile() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                777
            )
        } else {
            selectFileUpload()
        }
    }

    private fun selectFileUpload() {
        UwMediaPicker
            .with(this)
            .setGalleryMode(UwMediaPicker.GalleryMode.ImageGallery)
            .setGridColumnCount(2)
            .setMaxSelectableMediaCount(1)
            .setLightStatusBar(true)
            .setCompressFormat(Bitmap.CompressFormat.JPEG)
            .setCompressionMaxWidth(709F)
            .setCompressionMaxHeight(640F)
            .enableImageCompression(true)
            .setCompressionQuality(50)
            .setCompressedFileDestinationPath(this@BuatPostinganActivity.filesDir.path)
            .setCancelCallback {
                selectedFiles.clear()
            }
            .launch { f ->
                f?.let { files ->
                    selectedFiles.clear()
                    selectedFiles.addAll(files.map { File(it.mediaPath) })
                    if (files.isNotEmpty()) {
                        Glide
                            .with(this)
                            .load(files[0].mediaPath)
                            .into(binding.imgPreview)
                        showHideImagePreview(true)
                    }
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 777) {
            if (
                grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                selectFileUpload()
            } else {
                Toast.makeText(
                    this,
                    "The app needs your permission",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
