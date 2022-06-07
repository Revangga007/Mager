package com.mager.gamer.ui.user

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.anilokcun.uwmediapicker.UwMediaPicker
import com.anilokcun.uwmediapicker.model.UwMediaPickerMediaType
import com.bumptech.glide.Glide
import com.mager.gamer.base.BaseActivity
import com.mager.gamer.databinding.ActivityEditProfileBinding
import com.mager.gamer.ui.user.edit.BioUserActivity
import com.mager.gamer.ui.user.edit.LocUserActivity
import com.mager.gamer.ui.user.edit.NameUserActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class UserSettingActivity : BaseActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: SettingViewModel by viewModels()
    private var selectedFiles = mutableListOf<File>()
    private val File.size get() = if (!exists()) 0.0 else length().toDouble()
    private val File.sizeInKb get() = size / 1024
    private val File.sizeInMb get() = sizeInKb / 1024


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgLeft.setOnClickListener {
            finish()
        }
        binding.btnEdit.setOnClickListener {
            requestAccessForFile()
        }
        binding.txtEdtNama.setOnClickListener {
            val i = Intent(this, NameUserActivity::class.java)
            startActivity(i)
        }
        binding.txtEdtBio.setOnClickListener {
            val i = Intent(this, BioUserActivity::class.java)
            startActivity(i)
        }
        binding.txtEdtLoc.setOnClickListener {
            val i = Intent(this, LocUserActivity::class.java)
            startActivity(i)
        }
        binding.btnOut.setOnClickListener {

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
            .setLightStatusBar(true)
            .setMaxSelectableMediaCount(1)
            .setGalleryMode(UwMediaPicker.GalleryMode.ImageGallery)
            .setGridColumnCount(2)
            .enableImageCompression(true)
            .setCompressFormat(Bitmap.CompressFormat.JPEG)
            .setCompressionMaxWidth(709F)
            .setCompressionMaxHeight(640F)
            .setCompressionQuality(50)
            .setCompressedFileDestinationPath(this@UserSettingActivity.filesDir.path)
            .launch { f ->
                f?.let { files ->
                    selectedFiles.clear()
                    files.forEach {
                        val file = File(it.mediaPath)
                        if (it.mediaType == UwMediaPickerMediaType.IMAGE) {
                            if (file.sizeInMb <= 5.0) {
                                selectedFiles.add(File(it.mediaPath))
                                Glide
                                    .with(this)
                                    .load(it.mediaPath)
                                    .into(binding.imgPhoto)
                            } else {
                                Toast.makeText(
                                    this,
                                    "Maksimum foto yang dipilih harus < 5 MB",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
    }

    override fun setupObserver() {
        TODO("Not yet implemented")
    }
}