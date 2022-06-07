package com.mager.gamer.ui.user.edit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mager.gamer.databinding.ActivityEditNameBinding
import com.mager.gamer.databinding.ActivityEditPostBinding
import com.mager.gamer.ui.postingan.EditPostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NameUserActivity: AppCompatActivity() {
    private lateinit var binding: ActivityEditNameBinding
    private val viewModel: EditPostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgLeft.setOnClickListener {
            finish()
        }

    }
}
