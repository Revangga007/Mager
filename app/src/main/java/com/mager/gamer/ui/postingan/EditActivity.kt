package com.mager.gamer.ui.postingan

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mager.gamer.R
import com.mager.gamer.data.model.remote.postingan.edit.CreatedBy
import com.mager.gamer.data.model.remote.postingan.edit.EditBody
import com.mager.gamer.data.model.remote.postingan.get.Data
import com.mager.gamer.databinding.ActivityEditPostBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditPostBinding
    private val viewModel: EditPostViewModel by viewModels()
    private lateinit var postingan: Data


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgLeft.setOnClickListener {
            finish()
        }

        binding.edtStatus.doAfterTextChanged {
            if (!it.isNullOrEmpty()) {
                val text = "${it.length}/300"
                binding.txtKarakter.text = text
            }
        }
        binding.edtStatus.setText(postingan.postText)
        intent.extras?.getParcelable<Data>("post")?.let {
            postingan = it
            if (it.files != null) {
                Glide.with(binding.imgPreview.context)
                    .load(it.files)
                    .error(R.drawable.logo_mager_1)
                    .into(binding.imgPreview)
                binding.imgPreview.visibility = View.VISIBLE
            } else if (postingan.linkLivestream != null) {
                Glide.with(binding.imgPreview.context)
                    .load(postingan.linkLivestream)
                    .error(R.drawable.logo_mager_1)
                    .into(binding.imgPreview)
            }
        }
        setupObserver()
    }

    private fun setupObserver() {
        val loading = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loading.show() else loading.dismiss()
        }
        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        viewModel.editPost.observe(this) {
            Toast.makeText(this, "sukses edit post" , Toast.LENGTH_SHORT).show()
            finish()
            }
        }
    }
