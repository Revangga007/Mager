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
import androidx.lifecycle.lifecycleScope
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.bumptech.glide.Glide
import com.mager.gamer.databinding.ActivityBuatPostinganBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class BuatPostinganActivity : AppCompatActivity() {
    companion object {
        const val INTENT_IMAGE_MODE = "INTENT_IMAGE_MODE"
        const val INTENT_LIVE_MODE = "INTENT_LIVE_MODE"
    }

    private val File.size get() = if (!exists()) 0.0 else length().toDouble()
    private val File.sizeInKb get() = size / 1024
    private val File.sizeInMb get() = sizeInKb / 1024

    private lateinit var binding: ActivityBuatPostinganBinding
    private val viewModel: BuatPostinganViewModel by viewModels()
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
        binding.btnSend.setOnClickListener {
            val postText = binding.edtStatus.text.toString().trim()
            val linkStream = binding.edtLink.text.toString().trim()
            lifecycleScope.launch {
                when {
                    isImageMode -> {
                        if (selectedFiles.isEmpty()) {
                            Toast.makeText(
                                this@BuatPostinganActivity,
                                "Anda harus memilih file",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            viewModel.uploadImageToServer(selectedFiles[0])
                        }
                    }
                    isLiveMode -> {
                        viewModel.createPostingan(postText, linkStream, null)
                    }
                    else -> {
                        viewModel.createPostingan(postText, null, null)
                    }
                }
            }

        }
        setupObserver()
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
            .setGalleryMode(UwMediaPicker.GalleryMode.ImageAndVideoGallery)
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
                    files.forEach {
                        val file = File(it.mediaPath)
                        if (file.sizeInMb <= 5.0 && file.name.endsWith(".jpeg", true)) {
                            selectedFiles.add(File(it.mediaPath))
                        } else if (file.sizeInMb <= 10.0 && file.name.endsWith(".mp4", true)) {
                            selectedFiles.add(File(it.mediaPath))
                        }
                    }
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
                    "Aplikasi ini butuh izin akses",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setupObserver() {
        viewModel.loading.observe(this) {
        }
        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        viewModel.uploadResponse.observe(this) {
            lifecycleScope.launch {
                val postText = binding.edtStatus.text.toString().trim()
                viewModel.createPostingan(postText, null, null)
            }
        }
        viewModel.postinganResponse.observe(this) {
            Toast.makeText(this, "sukses buat post", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

//    private fun requestAccessForVideo() {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                777
//            )
//        } else {
//            selectVideoUpload()
//        }
//    }

//    private fun selectVideoUpload() {
//        UwMediaPicker
//            .with(this)
//            .setGalleryMode(UwMediaPicker.GalleryMode.VideoGallery)
//            .setGridColumnCount(2)
//            .setMaxSelectableMediaCount(1)
//            .setLightStatusBar(true)
//            .setCancelCallback {
//                selectedFiles.clear()
//            }
//            .launch { f ->
//                f?.let { files ->
//                    selectedFiles.clear()
//                    files.forEach {
//                        val file = File(it.mediaPath)
//                        if (file.sizeInMb <= 10.0) {
//                            selectedFiles.add(File(it.mediaPath))
//                        }
//                    }
//                    if (files.isNotEmpty()) {
//                        Glide
//                            .with(this)
//                            .load(files[0].mediaPath)
//                            .into(binding.imgPreview)
//                        showHideImagePreview(true)
//                    }
//                }
//            }
//    }
}
