package com.mager.gamer.ui.user.edit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mager.gamer.data.model.remote.user.detail.Data
import com.mager.gamer.data.model.remote.user.edit.EditUserBody
import com.mager.gamer.databinding.ActivityEditBioBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import com.mager.gamer.ui.user.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BioUserActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEditBioBinding
    private lateinit var dataUser: Data
    private val viewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditBioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgLeft.setOnClickListener {
            finish()
        }
        intent.extras?.getParcelable<Data>("data")?.let {
            dataUser = it
            binding.txtName.setText(it.biodata)
        }
//        binding.btnSimpan.setOnClickListener {
//            lifecycleScope.launch {
//                val bio = binding.txtName.text.toString()
//                val body = EditUserBody(null, bio, null)
//                viewModel.editUser(body)
//            }
//
//        }
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
        viewModel.editUserResponse.observe(this) {
            Toast.makeText(this, "sukses edit bio", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}